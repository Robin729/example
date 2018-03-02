import sun.misc.Unsafe;

import java.util.concurrent.locks.LockSupport;

/**
 * @author weijin
 */
public class AbstractQueuedSynchronizerCopy extends AbstractOwnableSynchronizerCopy {

  protected AbstractQueuedSynchronizerCopy() { }

  static final class Node {
    /** 声明一个共享模式的节点 */
    static final Node SHARED = new Node();
    /** 声明一个排他模式的节点 */
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

    // Used to establish initial head or SHARED marker
    Node() {
    }

    // Used by addWaiter
    Node(Thread thread, Node mode) {
      this.nextWaiter = mode;
      this.thread = thread;
    }

    // Used by Condition
    Node(Thread thread, int waitStatus) {
      this.waitStatus = waitStatus;
      this.thread = thread;
    }
  }

  private volatile Node head;
  private volatile Node tail;

  /**
   * state用于设定可重入锁的状态的
   */
  private volatile int state;
  protected final int getState() { return state; }
  protected final void setState(int newState) { state = newState; }
  protected final boolean compareAndSetState(int expect, int update) {
    return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
  }

  // 代替park对自旋时间，性能更好
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
      if (t == null) { // head和tail需要先初始化，null则初始化
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
   * 逻辑 增加一个等待的节点，节点的Node中包含了Thread对象
   * 1.新建一个节点，节点成员是currentThread和下一个节点
   * 2.把新建到节点添加到链表尾部
   * 3.返回的是这个新建的节点
   */
  private Node addWaiter(Node mode) {
    // 当前线程记录在node节点，node.nextWaiter = mode
    Node node = new Node(Thread.currentThread(), mode);
    Node pred = tail;
    // 这是快速到添加链表到结尾，为了快速
    if (pred != null) {
      node.prev = pred;
      if (compareAndSetTail(pred, node)) {
        pred.next = node;
        return node;
      }
    }
    // 循环添加到链表结尾
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
   * 1.当前节点node.waitStatus < 0 即非取消状态则设置为0，失败了也没关系
   * 2.如果下一个节点node.next.waitStatus != cancelled状态的节点,则从tail开始查找不是cancelled状态的节点
   * 3.如果找到了不是cancelled状态的节点就unpark(node.thread)，即唤醒在这个节点等待的线程
   */
  private void unparkSuccessor(Node node) {
    int ws = node.waitStatus;
    if (ws < 0)
      compareAndSetWaitStatus(node, ws, 0);

    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
      s = null;
      for (Node t = tail; t != null && t != node; t = t.prev) {
        if (t.waitStatus <= 0)
          s = t;
      }
    }

    if (s != null)
      LockSupport.unpark(s.thread);
  }

  /**
   * 逻辑 就是signal信号的线程唤醒动作
   * 1.初始化了且不是dummy的node
   * 2.如果waitStatus==0(0是中间的状态
   * 3.如果head变更，就会循环执行，就是释放过程中head的状态需要保持一致
   */
  private void doReleaseShared() {
    for (;;) {
      Node h = head;
      if (h != null && h != tail) {
        int ws = h.waitStatus;
        if (ws == Node.SIGNAL) {
          if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
            continue;
          unparkSuccessor(h);
        }
        else if (ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
          continue;
      }
      if (h == head)
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

  

  private static final Unsafe unsafe = Unsafe.getUnsafe();
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
