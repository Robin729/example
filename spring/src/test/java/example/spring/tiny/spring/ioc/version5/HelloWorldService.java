package example.spring.tiny.spring.ioc.version5;

import java.io.OutputStream;

public class HelloWorldService {

    private String text;

    private OutputService outputService;

    public void helloWorld() { outputService.output(text); }

    public void setText(String text) { this.text = text; }

    public void setOutputService(OutputService outputService) { this.outputService = outputService; }
}
