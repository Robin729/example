package example.spring.tiny.spring.ioc.version2;

import example.spring.tiny.spring.ioc.version2.factory.AutowireCapableBeanFactory;
import example.spring.tiny.spring.ioc.version2.factory.BeanFactory;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() {
        // 1.初始化beanFactory
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2.注册bean
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("example.spring.tiny.spring.ioc.version2.HelloWorldService");
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 3.使用bean
        HelloWorldService helloWorldService = (HelloWorldService)beanFactory.getBean("helloWorldService");
        helloWorldService.sayHello();
    }
}
