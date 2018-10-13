package example.effective.java.javadesignpatterns.CompletableFuture;

import java.util.concurrent.CompletableFuture;

public class App {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1");
        });
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3");
        });

        t3.start();
        t2.start();
        t1.start();


//        App app = new App();
//        app.test();
    }

    public void test() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    result.append(s);
                });
        System.out.println(result);
    }

    public void test2() {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void>cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s ->result.append(s));
        cf.join();
        System.out.println(result);

    }
}
