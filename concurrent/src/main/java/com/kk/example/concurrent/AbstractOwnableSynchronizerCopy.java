package com.kk.example.concurrent;

/**
 * @author weijin
 */
public abstract class AbstractOwnableSynchronizerCopy {

  protected AbstractOwnableSynchronizerCopy() { }

  private Thread exclusiveOwnerThread;

  protected final void setExclusiveOwnerThread(Thread thread) {
    exclusiveOwnerThread = thread;
  }

  protected final Thread getExclusiveOwnerThread() { return exclusiveOwnerThread; }
}
