package example.effective.java.javadesignpatterns.acyclicvistor.practice;

public class App {

    public static void main(String[] args) {
        // test AModem
        AModem aModem = new AModem();
        ModemVisitor visitor = new AModemVisitor() {
            @Override
            public void visit(AModem aModem) {
                aModem.test(2);
            }
        };
        aModem.accept(visitor);

        // test BModem
        BModem bModem = new BModem();
        ModemVisitor visitor1 = new BModemVisitor() {
            @Override
            public void visit(BModem bModem) {
                bModem.testB();
            }
        };
        bModem.accept(visitor1);

        // test AllModemVisitor
        ModemVisitor allVisitor = new AllModemVisitor() {
            @Override
            public void visit(AModem aModem) {
                aModem.test(5);
            }

            @Override
            public void visit(BModem bModem) {
                bModem.testB();
            }
        };

        aModem.accept(allVisitor);
        bModem.accept(allVisitor);
    }
}
