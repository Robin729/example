package example.effective.java.javadesignpatterns.acyclicvistor;

public class BModemConcreteVisitor implements BModemVisitor {
    @Override
    public void visit(BModem bModem) {
        System.out.println(bModem.toString());
    }
}
