package example.spring.tiny.spring.ioc.version6.context;


import example.spring.tiny.spring.ioc.version6.HelloWorldService;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc6.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
