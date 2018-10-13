package example.effective.java.javadesignpatterns.bridge;

public class FlyingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        System.out.println("flying.onActivate");
    }

    @Override
    public void apply() {
        System.out.println("flying.apply");
    }

    @Override
    public void onDeactivate() {
        System.out.println("flying.onDeactivate");
    }
}
