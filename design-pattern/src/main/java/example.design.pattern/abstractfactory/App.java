package example.effective.java.javadesignpatterns.abstractfactory;

public class App {

    private King king;

    private Castle castle;

    private Army army;

    public static void main(String[] args) {
        KingdomFactory q = FactoryMaker.getFactory(FactoryMaker.KingdomType.P);
        App qApp = new App();
        qApp.setArmy(q.createArmy());
        qApp.setCastle(q.createCastle());
        qApp.setKing(q.createKing());
        System.out.println(qApp);

        KingdomFactory p = FactoryMaker.getFactory(FactoryMaker.KingdomType.Q);
        App pApp = new App();
        pApp.setArmy(p.createArmy());
        pApp.setCastle(p.createCastle());
        pApp.setKing(p.createKing());
        System.out.println(pApp);
    }

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    @Override
    public String toString() {
        return "App{" +
                "king=" + king +
                ", castle=" + castle +
                ", army=" + army +
                '}';
    }

    public static class FactoryMaker {

        public enum KingdomType {
            Q, P
        }

        public static KingdomFactory getFactory(KingdomType kingdomType) {
            switch (kingdomType) {
                case P:
                    return new PKingdomFactory();
                case Q:
                    return new QKingdomFactory();
                default:
                    throw new IllegalArgumentException("KingdomType not supported");
            }
        }
    }
}
