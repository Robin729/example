package example.effective.java.javadesignpatterns.acyclicvistor.practice;

public class AModem implements Modem {

    @Override
    public void accept(ModemVisitor modemVisitor) {
        try {
            AModemVisitor visitor = (AModemVisitor) modemVisitor;
            visitor.visit(this);
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    public void test(int i) {
        System.out.println("number" + i);
    }
}
