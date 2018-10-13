package example.effective.java.javadesignpatterns.acyclicvistor;

public class App {

    public static void main(String[] args) {
        // 相当于回调
        AModemConcreteVisitor aModemConcreteVisitor = new AModemConcreteVisitor();
        BModemConcreteVisitor bModemConcreteVisitor = new BModemConcreteVisitor();

        Modem aModem = new AModem();
        Modem bModem = new BModem();

        // 接受对方的回调，回调中定义了可以使用的东西
        aModem.accept(aModemConcreteVisitor);
        bModem.accept(bModemConcreteVisitor);
    }
}
