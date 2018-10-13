package example.effective.java.javadesignpatterns.abstractfactory.example1;

public class BKingdomFactory implements KingdomFacotry {

    @Override
    public King createKing() {
        return new BKing();
    }

    @Override
    public Castle createCastle() {
        return new BCastle();
    }
}
