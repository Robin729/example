package example.effective.java.javadesignpatterns.builder;

public enum  Weapon {

    DAGGER,
    SWORD,
    AXE,
    WARHAMMER,
    BOW;

    public String toString() {
        return name().toLowerCase();
    }
}
