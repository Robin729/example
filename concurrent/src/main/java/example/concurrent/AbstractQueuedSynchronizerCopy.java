package example.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.LockSupport;

/**
 * @author weijin
 */
public abstract class AbstractQueuedSynchronizerCopy extends AbstractOwnableSynchronizerCopy {

  protected AbstractQueuedSynchronizerCopy() { }

  static final class Node {
    static final Node SHARED = new Node();
    static final Node EXCLUSIVE = null;

    static final int CANCELLED = 1;
    static final int SIGNAL = -1;
    static final int CONDITION = -2;
    static final int PROPAGATE = -3;

    volatile int waitStatus;
    volatile Node prev;
    volatile Node next;
    volatile Thread thread;
    Node nextWaiter;

    final boolean isShared() { return nextWaiter == SHARED; }
    final Node predecessor() throws NullPointerException {
      Node p = prev;
      if (p == null)
        throw new NullPointerException();
      else
        return p;
    }

    Node() { }
    Node(Thread thread, Node mode) {
      this.thread = thread;
      nextWaiter = mode;
    }
    Node(Thread thread, int waitStatus) {
      this.thread = thread;
      this.waitStatus = waitStatus;
    }
  }

  private volatile Node head;
  private volatile Node tail;

  /**
   * state用于设定可重入锁的状态的
   */
  private volatile int state;
  protected final int getState() { return state; }
  protected final void setState(int state) { this.state = state; }
  protected final boolean compareAndSetState(int expect, int update) {
    return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
  }

  static final long spinForTimeoutThreshold = 1000L;

  /**
   * 双向链表  在尾部添加一个节点，有一个dummy的node为初始值
   * 1.链表初始化为一个new node
   * 2.链表操作包括设置head和tail都是原子操作
   * 逻辑
   * 1.没有初始化先初始化
   * 2.初始化了就添加到链表尾部。添加过程是原子到且不成功会while直到添加成功
   */
  private Node enq(final Node node) {
    for (;;) {
      Node t = tail;
      if (t == null) { // Must initialize
        if (compareAndSetHead(new Node()))
          tail = head;
      } else {
        node.prev = t;
        if (compareAndSetTail(t, node)) {
          t.next = node;
          return t;
        }
      }
    }
  }

  /**
   * 使用head出初始化也可以，但显然没有原版优雅，因为多用了一个h变量
   */
  private Node enqCopy(final Node node) {
    for (;;) {
      Node h = head;
      if (h == null) {
        if (compareAndSetTail(null, new Node()))
          head = tail;
      } else {
        Node t = tail;
        node.prev = t;
        if (compareAndSetTail(t, tail)) {
          t.next = node;
          return t;
        }
      }
    }
  }

  /**
   * 逻辑 增加一个等待的节点，节点的Node中包含了Thread对象
   * 1.新建一个节点，节点成员是currentThread和下一个节点
   * 2.把新建到节点添加到链表尾部
   * 3.返回的是这个新建的节点
   */
  private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);
    Node pred = tail;
    if (pred != null) {
      node.prev = pred;
      if (compareAndSetTail(pred, node)) {
        pred.next = node;
        return node;
      }
    }

    enq(node);
    return node;
  }

  private void setHead(Node node) {
    head = node;
    node.thread = null;
    node.prev = null;
  }

  /**
   * 逻辑  唤醒一个node（不包括当前节点和waitStatus为Cancelled的节点）后面的节点中休眠的线程
   * 1.找到最靠近node的非cancelled的节点唤醒一次
   * 2.当前node状态尝试一次设置为0
   */
  private void unparkSuccessor(Node node) {
    int ws = node.waitStatus;
    if (ws < 0)
      compareAndSetWaitStatus(node, ws, 0);

    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
      s = null;
      for (Node t = tail; t != null && t != node; t = t.prev)
        if (t.waitStatus <= 0)
          s = t;
    }
    if (s != null)
      LockSupport.unpark(s.thread);
  }

  /**
   * node:清除状态并出队
   * 清除node的状态
   * 1.node.thread = null
   * 2.node.waitStatus = Node.CANCELLED
   * 出队逻辑
   * 1.跳过cancelled的节点
   *   pred = node的前面的节点中第一个waitStatus != cancelled的节点
   *   node.prev = pred
   *   predNext = pred.next # 是cancelled的node或node
   * 2.出队逻辑以pred处于哪个位置分为3种情况
   *   2.1 node == tail && CAS.this.tail = pred  # node == tail的情况，设置tail为pred
   *              CAS.pred.next = null           # 原子操作的设置pred.next = null
   *   2.2 条件,3个条件都必须满足，即&&
   *          1.pred ！= head
   *          2.pred.waitStatus == signal 或者
   *            pred.waitStatus <= 0 && CAS.pred.waitStatus = signal
   *          3.pred.thread != null
   *       条件满足后则执行如果
   *       node.next != null && nodex.next.waitStatus <= 0
   *       则 CAS.pred.next = next
   *          node.next = node 便于GC
   *   2.3 其他条件
   *       唤醒node后面的节点并且node.next = node
   */
  private void cancelAcquire(Node node) {
    if (node == null)
      return;

    node.thread = null;
    Node pred = node.prev;

    // 跳过取消的节点，并且将node.prev指向最终不是cancelled的节点
    while (pred.waitStatus > 0)
      node.prev = pred = pred.prev;
    Node predNext = pred.next;
    node.waitStatus = Node.CANCELLED;

    // 在尾部就直接移除自己
    if (node == tail && compareAndSetTail(node, pred)) {
      compareAndSetNext(pred, predNext, null);
    } else {
      int ws;
      if (pred != head && ((ws = pred.waitStatus) == Node.SIGNAL || (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node
              .SIGNAL))) && pred.thread != null) {
        Node next = node.next;
        if (next != null && next.waitStatus <= 0)
          compareAndSetNext(pred, predNext, next);
      } else {
        unparkSuccessor(node);
      }
      node.next = node; // help GC
    }
  }

  /**
   * 逻辑  如果线程应该block就返回true
   * 1.pred.waitStatus = signal  return true  前驱节点状态是signal就block当前节点（返回true），否则就返回false
   * 2.循环清理cancelled的前驱节点，直到有一个pred不是cancelled的状态
   * 3.前驱节点如果是非cancelled非signal。就CAS.pred.waitStatus = signal,不关心是否成功
   */
  private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
      return true;
    if (ws > 0) {
      do {
        node.prev = pred = pred.prev;
      } while (pred.waitStatus > 0);
      pred.next = node;
    } else {
      compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
  }

  static void selfInterrupt() { Thread.currentThread().interrupt(); }

  private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
  }

  final boolean acqureQueued(final Node node, int arg) {
    boolean failed = true;
    try {
      boolean interrupted = false;
      for (;;) {
        final Node p = node.predecessor();
        if (p == head /*&& tryAcquire(arg)*/) {
          setHead(node);
          p.next = null;
          failed = false;
          return interrupted;
        }
        if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
          interrupted = true;
      }
    } finally {
      if (failed)
        cancelAcquire(node);
    }
  }

  public final void acquire(int arg) {
    if (!tryAcquire(arg) && acqureQueued(addWaiter(Node.EXCLUSIVE), arg))
      selfInterrupt();
  }

  /**
   * 逻辑 就是signal信号的线程唤醒动作
   * 1.初始化了且不是dummy的node
   *    ws == signal
   *      如果!(CAS.h.ws = 0) 则 重复循环
   *      成功 则unparkSuccessor(h);
   *    ws == 0 && !CAS.h.ws = propagate 则 重复循环
   * 2.如果head变更，就会循环执行，就是释放过程中head的状态需要保持一致
   */
  private void doReleaseShared() {
    for (;;) {
      Node h = head;
      if (h != null && h != tail) {
        int ws = h.waitStatus;
        if (ws == Node.SIGNAL) {
          if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
            continue;            // loop to recheck cases
          unparkSuccessor(h);
        }
        else if (ws == 0 &&
                !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
          continue;                // loop on failed CAS
      }
      if (h == head)                   // loop if head changed
        break;
    }
  }

  private void setHeadAndPropagate(Node node, int propagate) {
    Node h = head;
    setHead(node);
    if (propagate > 0 || h == null || h.waitStatus < 0 || (h = head) == null || h.waitStatus < 0) {
      Node s = node.next;
      if (s == null || s.isShared())
        doReleaseShared();
    }
  }


  protected boolean tryAcquire(int arg) {
    throw new UnsupportedOperationException();
  }

  private static final Unsafe unsafe;

  // steal the Unsafe
  static {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      unsafe = (Unsafe) f.get(null);
    } catch (Exception ex) {
      throw new Error(ex);
    }
  }

  private static final long stateOffset;
  private static final long headOffset;
  private static final long tailOffset;
  private static final long waitStatusOffset;
  private static final long nextOffset;

  static {
    try {
      stateOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerCopy.class.getDeclaredField("state"));
      headOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerCopy.class.getDeclaredField("head"));
      tailOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerCopy.class.getDeclaredField("tail"));
      waitStatusOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerCopy.class.getDeclaredField("waitStatus"));
      nextOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizerCopy.class.getDeclaredField("next"));
    } catch (Exception ex) { throw new Error(ex); }
  }

  /** 下面就是提供原子的CAS操作，所有的方法都是对unsafe对封装 */
  private final boolean compareAndSetHead(Node update) {
    return unsafe.compareAndSwapObject(this, headOffset, null, update);
  }

  private final boolean compareAndSetTail(Node expect, Node update) {
    return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
  }

  private static final boolean compareAndSetWaitStatus(Node node, int expect, int update) {
    return unsafe.compareAndSwapInt(node, waitStatusOffset, expect, update);
  }

  private static final boolean compareAndSetNext(Node node, Node expect, Node update) {
    return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
  }
}
