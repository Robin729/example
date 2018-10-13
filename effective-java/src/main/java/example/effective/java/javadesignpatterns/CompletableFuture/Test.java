package example.effective.java.javadesignpatterns.CompletableFuture;

import java.sql.SQLOutput;
import java.util.Hashtable;

public class Test {

    public static void main(String[] args) {
        System.out.println(getValue(2));
    }
    public static int getValue(int i) {
        int result = 0;
        switch (i) {
            case 1:
                System.out.println(result);
                result = result + i;
            case 2:
                result = result + i * 2;
                System.out.println(result);
            case 3:
                System.out.println(result);
                result = result + i * 3;
        }
        return result;
    }
}
