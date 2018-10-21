package example.spring.tiny.spring.ioc.version3;

import example.spring.tiny.spring.ioc.version3.factory.AutowireCapableBeanFactory;
import example.spring.tiny.spring.ioc.version3.factory.BeanFactory;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {
        // 1.初始化bean工厂
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2.bean定义
        BeanDefinition beanDefinition = new BeanDefinition();
        // 设置bean的时候会把类的完整包名和class设置好，但暂时不会创建对象
        beanDefinition.setBeanClassName("example.spring.tiny.spring.ioc.version3.HelloWorldService");

        // 3.设置属性
        // 也就是注入属性，这里其实就用到了反射的性质
        PropertyValues propertyValues = new PropertyValues();
        // HelloWorldService的成员text的属性注入
        propertyValues.addPropertyValue(new PropertyValue("text", "Hello World3!"));
        beanDefinition.setPropertyValues(propertyValues);

        // 4.注册bean，
        // 调用链会初始化bean
        // 将属性注入bean
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 5.获取并使用bean
        // 通过名字获取的bean
        HelloWorldService helloWorldService = (HelloWorldService)beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
