package example.effective.java.javadesignpatterns.adapter;

public class FishingBoatAdapter implements RowingBoat {

    private final FishingBoat fishingBoat;

    public FishingBoatAdapter(FishingBoat fishingBoat) {
        this.fishingBoat = fishingBoat;
    }

    @Override
    public void row() {
        fishingBoat.sail();
    }
}
