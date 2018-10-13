package example.effective.java.javadesignpatterns.bridge;

public class SoulEatingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        System.out.println("Soul.onActivate");
    }

    @Override
    public void apply() {
        System.out.println("Soul.apply");
    }

    @Override
    public void onDeactivate() {
        System.out.println("Soul.onDeactivate");
    }
}
