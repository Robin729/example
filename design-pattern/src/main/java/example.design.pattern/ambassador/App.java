package example.effective.java.javadesignpatterns.ambassador;

public class App {

    public static void main(String[] args) throws Exception {
        Client host1 = new Client();
        Client host2 = new Client();
        host1.useService(12);
        host2.useService(73);
    }
}
