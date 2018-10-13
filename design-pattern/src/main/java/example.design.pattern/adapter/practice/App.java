package example.effective.java.javadesignpatterns.adapter.practice;

public class App {

    public static void main(String[] args) {
        RowingBoat rowingBoat = new FishingBoatAdapter(new FishingBoat());

        rowingBoat.row();
    }
}
