package example.effective.java.javadesignpatterns.builder;

public enum  HairColor {

    WHITE,
    BLOND,
    RED,
    BROWN,
    BLACK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static void main(String[] args) {

    }
}
