package example.effective.java.javadesignpatterns.bridge;

public class Sword implements Weapon {

    private final Enchantment enchantment;

    public Sword(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        System.out.println("Sword.wield");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        System.out.println("Sword.swing");
        enchantment.apply();
    }

    @Override
    public void unWield() {
        System.out.println("Sword.unWield");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }

    @Override
    public String toString() {
        return "Sword{" +
                "enchantment=" + enchantment +
                '}';
    }
}
