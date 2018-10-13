package example.effective.java.javadesignpatterns.bridge.example1;

public class A implements IA {

    private final IB ib;

    public A(IB ib) {
        this.ib = ib;
    }

    @Override
    public void doSomething() {
        System.out.println("A");
        ib.doSomething();
    }
}
