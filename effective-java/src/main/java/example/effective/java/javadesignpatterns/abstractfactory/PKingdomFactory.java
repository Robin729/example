package example.effective.java.javadesignpatterns.abstractfactory;

public class PKingdomFactory implements KingdomFactory {

    @Override
    public Castle createCastle() {
        return new PCastle();
    }

    @Override
    public King createKing() {
        return new PKing();
    }

    @Override
    public Army createArmy() {
        return new PArmy();
    }
}
