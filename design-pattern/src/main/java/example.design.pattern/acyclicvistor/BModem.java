package example.effective.java.javadesignpatterns.acyclicvistor;

public class BModem extends Modem {

    @Override
    public void accept(ModemVisitor modemVisitor) {
        try {
            ((BModemVisitor)modemVisitor).visit(this);
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "BModem";
    }
}
