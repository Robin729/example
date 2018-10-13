package example.effective.java.javadesignpatterns.adapter.example1;

public class App {

    public static void main(String[] args) {

        NewInterface newInterface = new OldClassAdaptor(new OldClass());
        // 这里就实现了适配的功能，但适配过多会造成代码难于维护。
        newInterface.visit();
    }
}
