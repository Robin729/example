package example.effective.java.javadesignpatterns.builder;

public enum  Profession {

    WARRIOR,
    THIEF,
    MAGE,
    PRIEST;

    public String toString() {
        return name().toLowerCase();
    }
}
