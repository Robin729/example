package example.spring.tiny.spring.ioc.version5;

public class OutputService {

    private HelloWorldService helloWorldService;

    public void output(String text) {
        assert helloWorldService != null : "helloWorldService is null";
        System.out.println(text);
    }

    public void setHelloWorldService(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }
}
