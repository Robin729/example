package example.effective.java.javadesignpatterns.ambassador;

import static java.lang.Thread.sleep;

public class RemoteService implements RemoteServiceInterface {

    private static RemoteService service = null;

    public static RemoteService getInstance() {
        if (service == null) {
            service = new RemoteService();
        }
        return service;
    }

    @Override
    public long doRemoteFunction(int value) throws Exception {
        long waitTime = (long) Math.floor(Math.random() * 1000);

        try {
            sleep(waitTime);
        } catch (InterruptedException e) {
            // ignore
        }
        return waitTime >= 200 ? value * 10 : -1;
    }

    private RemoteService() {}
}
