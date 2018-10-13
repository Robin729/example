package example.effective.java.javadesignpatterns.abstractfactory.example1;

public class AKindomFacotry implements KingdomFacotry {

    @Override
    public King createKing() {
        return new AKing();
    }

    @Override
    public Castle createCastle() {
        return new ACastle();
    }
}
