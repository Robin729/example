package example.effective.java.javadesignpatterns.adapter.practice;

public class FishingBoatAdapter implements RowingBoat {

    private final FishingBoat fishingBoat;

    public FishingBoatAdapter(FishingBoat fishingBoat) {
        this.fishingBoat = fishingBoat;
    }

    @Override
    public void row() {
        System.out.println("Before row");
        fishingBoat.sail();
        System.out.println("After row");
    }
}
