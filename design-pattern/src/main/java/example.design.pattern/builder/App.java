package example.effective.java.javadesignpatterns.builder;

import static example.effective.java.javadesignpatterns.builder.HairColor.BLOND;
import static example.effective.java.javadesignpatterns.builder.Profession.MAGE;
import static example.effective.java.javadesignpatterns.builder.Profession.WARRIOR;

public class App {

    public static void main(String[] args) {
        Hero mage = Hero.newBuilder(WARRIOR, "Amberjill").withHairColor(BLOND).build();
        System.out.println(mage);
    }
}
