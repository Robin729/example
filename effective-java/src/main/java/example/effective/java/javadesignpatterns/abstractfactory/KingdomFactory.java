package example.effective.java.javadesignpatterns.abstractfactory;

public interface KingdomFactory {

    Castle createCastle();

    King createKing();

    Army createArmy();
}
