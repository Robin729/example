package example.effective.java.javadesignpatterns.adapter.example1;

public class OldClassAdaptor implements NewInterface {

    private final OldClass oldClass;

    public OldClassAdaptor(OldClass oldClass) {
        this.oldClass = oldClass;
    }

    @Override
    public void visit() {
        oldClass.doSomething();
    }
}
