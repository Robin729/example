package example.effective.java.javadesignpatterns.abstractfactory.example1;

public class App {

    public static void main(String[] args) {
        KingdomFacotry facotry = FactoryMaker.getFacotry(FactoryMaker.KingdomType.A);
    }
}
