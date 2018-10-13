package example.effective.java;

public class Test {
    static String a = new String("a");
    static String b = new String("b");
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (a) {
                try {
                    System.out.println("Thread1 get lock :"+a);
                    Thread.sleep(1000);
                    synchronized (b) {
                        System.out.println("Thread1 get lock :" + b);
                    }
                }
                catch (InterruptedException e) {
                }

            }
        }).start();
        new Thread(() -> {
            synchronized (b) {
                try {
                    System.out.println("Thread2 get lock :"+b);
                    Thread.sleep(1000);
                    synchronized (a) {
                        System.out.println("Thread2 get lock :"+a);
                    }
                }
                catch (InterruptedException e) {
                }
            }
        }).start();
    }
}
