package example.effective.java.javadesignpatterns.abstractfactory;

public class QKingdomFactory implements KingdomFactory {

    @Override
    public Castle createCastle() {
        return new QCastle();
    }

    @Override
    public King createKing() {
        return new QKing();
    }

    @Override
    public Army createArmy() {
        return new QArmy();
    }
}
