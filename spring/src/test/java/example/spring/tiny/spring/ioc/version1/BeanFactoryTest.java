package example.spring.tiny.spring.ioc.version1;

import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() {
        // 1.初始化Bean工厂
        BeanFactory beanFactory = new BeanFactory();

        // 2.新建bean并将bean注入
        BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldService());
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 3.获取bean并使用，即使用service
        HelloWorldService helloWorldService = (HelloWorldService)beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
