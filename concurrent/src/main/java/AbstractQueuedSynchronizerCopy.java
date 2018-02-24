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

  private volatile int state;
  protected final int getState() { return state; }
  protected final void setState(int newState) { state = newState; }
  protected final boolean compareAndSetState(int expect, int update) {
    return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
  }

  // 代替park对自旋时间，性能更好
  static final long spinForTimeoutThreshold = 1000L;

  /**
   * 双向链表，并且原子操作链表添加
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
   * 逻辑
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
   * 逻辑  唤醒node（不包括当前节点）后面的节点中休眠的线程
   * 1.当前节点node.waitStatus < 0 即非取消状态则设置为0，失败了也没关系
   * 2.如果下一个节点node.next.waitStatus = cancelled状态,则从tail开始查找不是cancelled状态的节点
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
