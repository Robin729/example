package example.spring.tiny.spring.ioc.version6;

public class OutputService {

    private HelloWorldService helloWorldService;

    public void output(String text){
        assert text != null;
        System.out.println(text);
    }

    public void setHelloWorldService(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }
}
