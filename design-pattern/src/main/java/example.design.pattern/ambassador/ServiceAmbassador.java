package example.effective.java.javadesignpatterns.ambassador;

import static java.lang.Thread.sleep;

public class ServiceAmbassador implements RemoteServiceInterface {

    private static final int RETRIES = 3;

    private static final long DELAY_MS = 3000;

    ServiceAmbassador() {}

    @Override
    public long doRemoteFunction(int value) throws Exception {
        return safeCall(value);
    }

    private long safeCall(int value) throws Exception {
        long result = -1;
        for (int i = 0; i < RETRIES; i++) {
            if ((result = checkLatency(value)) != -1) {
                break;
            } else {
                sleep(DELAY_MS);
            }
        }
        return result;
    }

    private long checkLatency(int value) throws Exception {
        long startTime = System.currentTimeMillis();
        long result = RemoteService.getInstance().doRemoteFunction(value);
        long timeTaken = System.currentTimeMillis() - startTime;
        return result;
    }
}
