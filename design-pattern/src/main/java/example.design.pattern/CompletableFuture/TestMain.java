package example.effective.java.javadesignpatterns.CompletableFuture;

import java.util.concurrent.ConcurrentHashMap;

public class TestMain {

    public void test(String str) {
        System.out.println("sb");
    }

    public void test(Object obj) {
        System.out.println("2b");
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.get(null);
    }

    public void test(TestMain testMain) {
        System.out.println("c");
    }

    public static void main(String[] args) {
        TestMain t = new TestMain();
    }

}
