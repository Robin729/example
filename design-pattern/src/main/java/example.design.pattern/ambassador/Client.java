package example.effective.java.javadesignpatterns.ambassador;

public class Client {

    private final ServiceAmbassador service = new ServiceAmbassador();

    long useService(int value) throws Exception {
        return service.doRemoteFunction(value);
    }
}
