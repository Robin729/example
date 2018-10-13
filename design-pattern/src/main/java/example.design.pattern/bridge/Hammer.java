package example.effective.java.javadesignpatterns.bridge;

public class Hammer implements Weapon {

    private final Enchantment enchantment;

    public Hammer(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        System.out.println("Hammer.weild");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        System.out.println("Hammer.swing");
        enchantment.apply();
    }

    @Override
    public void unWield() {
        System.out.println("Hammer.unWield");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }

    @Override
    public String toString() {
        return "Hammer{" +
                "enchantment=" + enchantment +
                '}';
    }
}
