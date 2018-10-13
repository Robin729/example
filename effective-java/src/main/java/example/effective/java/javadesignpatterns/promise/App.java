package example.effective.java.javadesignpatterns.promise;

import java.util.Map;
import java.util.concurrent.*;

public class App {

   public static void main(String[] args) throws InterruptedException {
       ExecutorService executor = Executors.newSingleThreadExecutor();

       Promise<Integer> promise = new Promise<>();
       promise.thenApply(n -> {
           System.out.println(n);
           return n + 1;
       }).thenApply(n -> {
           System.out.println(n);
           return n + 1;
       });
       // fulfillInAsync是个触发方法，必须在方法构造完成后才能调用，否则会导致thenApply后续的方法无法执行
       promise.fulfillInAsync(() -> 5, executor);

       // 不正确的使用
       Promise<Integer> promise1 = new Promise<Integer>().fulfillInAsync(() -> {
           System.out.println("只会执行到这里，后续的不会执行，因为当执行这个的时候后续的run没有添加进来");
           return 7;
       }, executor).thenApply(n -> {
           System.out.println(n);
           return n + 1;
       }).thenApply(n -> {
           System.out.println(n);
           return n + 1;
       });

       Thread.sleep(2000);
       executor.shutdown();
   }
}
