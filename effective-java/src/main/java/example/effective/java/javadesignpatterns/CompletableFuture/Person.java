package example.effective.java.javadesignpatterns.CompletableFuture;

import java.util.concurrent.CountDownLatch;

public class Person {
    static int arr[] = new int[10];

    public static void main(String a[]) {
        System.out.println(arr[1]);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        countDownLatch.countDown();
    }
}