package example.effective.java.javadesignpatterns.abstractfactory.example1;

public class FactoryMaker {

    public enum KingdomType {
        A, B;
    }

    public static KingdomFacotry getFacotry(KingdomType kingdomType) {
        switch (kingdomType) {
            case A:
                return new AKindomFacotry();
            case B:
                return new BKingdomFactory();
            default:
                throw new IllegalArgumentException("KingdomType not supported");
        }
    }
}
