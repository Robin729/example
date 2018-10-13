package example.effective.java.javadesignpatterns.bridge;

public class App {

    public static void main(String[] args) {
        Weapon sowrd = new Sword(new SoulEatingEnchantment());
        Weapon hammer = new Hammer(new FlyingEnchantment());

        sowrd.swing();
        sowrd.wield();
        sowrd.unWield();

        hammer.unWield();
        hammer.wield();
        hammer.swing();
    }
}
