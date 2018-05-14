package com.kk.example.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author weijin
 */
public class ConditionBoundedBuffer<T> {

  protected final Lock lock = new ReentrantLock();
  // 条件谓词：notFull (count < items.length)
  private final Condition notFull = lock.newCondition();
  // 条件谓词：notEmpty (count > 0)
  private final Condition notEmpty = lock.newCondition();

  private final int BUFFER_SIZE = 10;
  private final T[] items = (T[]) new Object[BUFFER_SIZE];
  private int tail, head, count;

  // 阻塞并直到: notFull
  public void put(T t) throws InterruptedException {
    lock.lock();
    try {
      while (count == items.length)
        notFull.await();
      items[tail] = t;
      if (++tail == items.length)
        tail = 0;
      ++count;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  // 阻塞并直到：notEmpty
  public T take() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0)
        notEmpty.await();
      T t = items[head];
      items[head] = null;
      if (++head == items.length)
        head = 0;
      --count;
      notFull.signal();
      return t;
    } finally {
      lock.unlock();
    }
  }
}
