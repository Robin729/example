package example.effective.java.javadesignpatterns.acyclicvistor;

public class AModemConcreteVisitor implements AModemVisitor {

    @Override
    public void visit(AModem aModem) {
        System.out.println(aModem.toString());
    }
}
