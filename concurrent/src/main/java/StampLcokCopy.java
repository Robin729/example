import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author weijin
 */
public class StampLcokCopy {

  // CPU核心数
  private static final int NCPU = Runtime.getRuntime().availableProcessors();
  // 最大尝试次数，如果是单核CPU就是0，不尝试。否则2^6 = 64次最大尝试次数
  private static final int SPINS = (NCPU > 1) ? 1 << 6 : 0;
  // head的最大尝试次数1024
  private static final int HEADS_SPINS = (NCPU > 1) ? 1 << 10 : 0;
  // max head 尝试次数2^16
  private static final int MAX_HEAD_SPINS = (NCPU > 1) ? 1 << 16 : 0;
  //
  private static final int OVERFLOW_YIELD_RATE = 7;
  //
  private static final int LG_READERS = 7;

  // lock state
  private static final long RUNIT = 1L;
  private static final long WBIT = 1L << LG_READERS; // 二进制        1000000 十进制128 左移7位
  private static final long RBITS = WBIT - 1L;       // 二进制         111111 十进制127 即低6位都是1
  private static final long RFULL = RBITS - 1L;      // 二进制         111110 十进制126
  private static final long ABITS = RBITS | WBIT;    // 二进制        1111110 十进制255
  private static final long SBITS = ~RBITS;          // 二进制        ~111111 十进制-128
  // state的初始值
  private static final long ORIGIN = WBIT << 1;      // 二进制       10000000 十进制256
  // 中断标志位
  private static final long INTERRUPTED = 1L;        // 二进制              1 十进制1

  // node status
  private static final int WAITING = -1;
  private static final int CANCELLED = 1;

  // node mode, read or write
  private static final int RMODE = 0;
  private static final int WMODE = 1;

  // node
  static final class WNode {
    volatile WNode prev;
    volatile WNode next;
    volatile WNode cowait;       // list of linked readers
    volatile Thread thread;      // non-null while possibly parked
    volatile int status;         // 0, WAITING, CANCELLED
    final int mode;              // RMODE or WMODE
    WNode(int m, WNode p) { mode = m; prev = p; }
  }

  // head and tail of CLH queue
  private transient volatile WNode whead;
  private transient volatile WNode wtail;

  // views
  transient ReadLockView readLockView;
  transient WriteLockView writeLockView;
  transient ReadWriteLockView readWriteLockView;

  private transient volatile long state;
  private transient int readerOverflow;

  public StampLcokCopy() { state = ORIGIN; }

  // 独占锁，不可中断
  public long writeLock() {
    long s, next;
    return ((((s = state) & ABITS) == 0L && U.compareAndSwapLong(this, STATE, s, next = s + WBIT)) ? next : acquireWrite(false, 0L));
  }

  private long acquireWrite(boolean interruptible, long deadline) {
    WNode node = null, p;
    for (int spins = -1;;) { // spin while enqueuing
      long m, s, ns;
      if ((m = (s = state) & ABITS) == 0L) {
        if (U.compareAndSwapLong(this, STATE, s, ns = s + WBIT))
          return ns;
      }
      else if (spins < 0)
        spins = (m == WBIT && wtail == whead) ? SPINS : 0;
      else if (spins > 0) {
        if (LockSupport.nextSecondarySeed() >= 0)
          --spins;
      }
      else if ((p = wtail) == null) { // initialize queue
        WNode hd = new WNode(WMODE, null);
        if (U.compareAndSwapObject(this, WHEAD, null, hd))
          wtail = hd;
      }
      else if (node == null)
        node = new WNode(WMODE, p);
      else if (node.prev != p)
        node.prev = p;
      else if (U.compareAndSwapObject(this, WTAIL, p, node)) {
        p.next = node;
        break;
      }
    }

    for (int spins = -1;;) {
      WNode h, np, pp; int ps;
      if ((h = whead) == p) {
        if (spins < 0)
          spins = HEAD_SPINS;
        else if (spins < MAX_HEAD_SPINS)
          spins <<= 1;
        for (int k = spins;;) { // spin at head
          long s, ns;
          if (((s = state) & ABITS) == 0L) {
            if (U.compareAndSwapLong(this, STATE, s,
                    ns = s + WBIT)) {
              whead = node;
              node.prev = null;
              return ns;
            }
          }
          else if (LockSupport.nextSecondarySeed() >= 0 &&
                  --k <= 0)
            break;
        }
      }
      else if (h != null) { // help release stale waiters
        WNode c; Thread w;
        while ((c = h.cowait) != null) {
          if (U.compareAndSwapObject(h, WCOWAIT, c, c.cowait) &&
                  (w = c.thread) != null)
            U.unpark(w);
        }
      }
      if (whead == h) {
        if ((np = node.prev) != p) {
          if (np != null)
            (p = np).next = node;   // stale
        }
        else if ((ps = p.status) == 0)
          U.compareAndSwapInt(p, WSTATUS, 0, WAITING);
        else if (ps == CANCELLED) {
          if ((pp = p.prev) != null) {
            node.prev = pp;
            pp.next = node;
          }
        }
        else {
          long time; // 0 argument to park means no timeout
          if (deadline == 0L)
            time = 0L;
          else if ((time = deadline - System.nanoTime()) <= 0L)
            return cancelWaiter(node, node, false);
          Thread wt = Thread.currentThread();
          U.putObject(wt, PARKBLOCKER, this);
          node.thread = wt;
          if (p.status < 0 && (p != h || (state & ABITS) != 0L) &&
                  whead == h && node.prev == p)
            U.park(false, time);  // emulate LockSupport.park
          node.thread = null;
          U.putObject(wt, PARKBLOCKER, null);
          if (interruptible && Thread.interrupted())
            return cancelWaiter(node, node, true);
        }
      }
    }
  }

  // view classes

  final class ReadLockView implements Lock {
    public void lock() { readLock(); }
    public void lockInterruptibly() throws InterruptedException {
      readLockInterruptibly();
    }
    public boolean tryLock() { return tryReadLock() != 0L; }
    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
      return tryReadLock(time, unit) != 0L;
    }
    public void unlock() { unstampedUnlockRead(); }
    public Condition newCondition() {
      throw new UnsupportedOperationException();
    }
  }

  final class WriteLockView implements Lock {
    public void lock() { writeLock(); }
    public void lockInterruptibly() throws InterruptedException {
      writeLockInterruptibly();
    }
    public boolean tryLock() { return tryWriteLock() != 0L; }
    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
      return tryWriteLock(time, unit) != 0L;
    }
    public void unlock() { unstampedUnlockWrite(); }
    public Condition newCondition() {
      throw new UnsupportedOperationException();
    }
  }

  final class ReadWriteLockView implements ReadWriteLock {
    public Lock readLock() { return asReadLock(); }
    public Lock writeLock() { return asWriteLock(); }
  }

  // Unsafe
  // Unsafe mechanics
  private static final sun.misc.Unsafe U;
  private static final long STATE;
  private static final long WHEAD;
  private static final long WTAIL;
  private static final long WNEXT;
  private static final long WSTATUS;
  private static final long WCOWAIT;
  private static final long PARKBLOCKER;

  // steal the Unsafe
  static {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      U = (Unsafe) f.get(null);
    } catch (Exception ex) {
      throw new Error(ex);
    }
  }

  static {
    try {
      Class<?> k = StampedLockCopy.class;
      Class<?> wk = WNode.class;
      STATE = U.objectFieldOffset
              (k.getDeclaredField("state"));
      WHEAD = U.objectFieldOffset
              (k.getDeclaredField("whead"));
      WTAIL = U.objectFieldOffset
              (k.getDeclaredField("wtail"));
      WSTATUS = U.objectFieldOffset
              (wk.getDeclaredField("status"));
      WNEXT = U.objectFieldOffset
              (wk.getDeclaredField("next"));
      WCOWAIT = U.objectFieldOffset
              (wk.getDeclaredField("cowait"));
      Class<?> tk = Thread.class;
      PARKBLOCKER = U.objectFieldOffset
              (tk.getDeclaredField("parkBlocker"));

    } catch (Exception e) {
      throw new Error(e);
    }
  }
}


}
