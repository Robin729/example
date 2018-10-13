package example.effective.java.javadesignpatterns.bridge.example1;

public class App {

    public static void main(String[] args) {
        // 桥接本质就是一个是另一个的成员，以接口为成员。
        IA ia = new A(new B());
        ia.doSomething();
    }
}
