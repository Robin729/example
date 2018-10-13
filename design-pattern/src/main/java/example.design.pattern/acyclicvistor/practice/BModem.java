package example.effective.java.javadesignpatterns.acyclicvistor.practice;

public class BModem implements Modem {

    @Override
    public void accept(ModemVisitor modemVisitor) {
        try {
            BModemVisitor visitor = (BModemVisitor) modemVisitor;
            visitor.visit(this);
        } catch(ClassCastException cce) {
            cce.printStackTrace();
        }

    }

    public void testB() {
        System.out.println("BModem");
    }
}
