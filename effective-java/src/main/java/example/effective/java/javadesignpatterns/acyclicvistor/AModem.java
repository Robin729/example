package example.effective.java.javadesignpatterns.acyclicvistor;

public class AModem extends Modem {
    @Override
    public void accept(ModemVisitor modemVisitor) {
        try {
            ((AModemVisitor)modemVisitor).visit(this);
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "AModem";
    }
}
