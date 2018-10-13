package example.effective.java.javadesignpatterns.builder;

public class Hero {

    private final Profession profession;
    private final String name;
    private final HairType hairType;
    private final Armor armor;
    private final Weapon weapon;

    private Hero(Builder builder) {
        this.profession = builder.profession;
        this.name = builder.name;
        this.hairType = builder.hairType;
        this.armor = builder.armor;
        this.weapon = builder.weapon;
    }

    public static Builder newBuilder(Profession profession, String name) {
        return new Hero.Builder(profession, name);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "profession=" + profession +
                ", name='" + name + '\'' +
                ", hairType=" + hairType +
                ", armor=" + armor +
                ", weapon=" + weapon +
                '}';
    }

    public static class Builder {

        private final Profession profession;
        private final String name;
        private HairType hairType;
        private HairColor hairColor;
        private Armor armor;
        private Weapon weapon;

        public Builder(Profession profession, String name) {
            if (profession == null || name == null)
                throw new IllegalArgumentException("profession and name can not be null");
            this.profession = profession;
            this.name = name;
        }

        public Builder withHairType(HairType hairType) {
            this.hairType = hairType;
            return this;
        }

        public Builder withHairColor(HairColor hairColor) {
            this.hairColor = hairColor;
            return this;
        }

        public Builder withArmor(Armor armor) {
            this.armor = armor;
            return this;
        }

        public Builder withWeapon(Weapon weapon) {
            this.weapon = weapon;
            return this;
        }

        public Hero build() {
            return new Hero(this);
        }
    }
}
