package example.effective.java.javadesignpatterns.acyclicvistor;

public abstract class Modem {

    public abstract void accept(ModemVisitor modemVisitor);
}
