import java.util.concurrent.atomic.AtomicReference;

/**
 * @author weijin
 */
public class ConcurrentStack<E> {
  AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

  public void push(E item) {
    Node newHead = new Node<E>(item);
    Node<E> oldHead;
    do {
      oldHead = top.get();
      newHead.next = oldHead;
    } while (!top.compareAndSet(oldHead, newHead));
  }

  public E pop() {
    Node oldHead;
    Node newHead;
    do {
      oldHead = top.get();
      if (oldHead == null)
        return null;
      newHead = oldHead.next;
    } while (!top.compareAndSet(oldHead, newHead));
    return (E)oldHead.item;
  }

  private static class Node<E> {
    public final E item;
    public Node<E> next;
    public Node(E item) {
      this.item = item;
    }
  }
}
