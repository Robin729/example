package example.effective.java.javadesignpatterns.acyclicvistor.practice;

public interface Modem {

    void accept(ModemVisitor modemVisitor);
}
