package example.effective.java;


public class Room implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    private static class State implements Runnable {
        int numJunkPiles;

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    private final State state;

    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable= cleaner.register(this, state);
    }

    @Override
    public void close() {
        cleanable.clean();
    }

    public static void main(String[] args) {
        try(Room myRoom = new Room(8)) {
            System.out.println("bye");
        }

        new Room(9);
        System.out.println("bye 9");
    }
}
