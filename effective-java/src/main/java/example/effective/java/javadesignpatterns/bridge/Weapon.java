package example.effective.java.javadesignpatterns.bridge;

public interface Weapon {

    void wield();

    void swing();

    void unWield();

    Enchantment getEnchantment();
}
