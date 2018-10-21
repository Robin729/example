package example.spring.tiny.spring.ioc.version4;

import example.spring.tiny.spring.ioc.version4.factory.AutowireCapableBeanFactory;
import example.spring.tiny.spring.ioc.version4.factory.BeanFactory;
import example.spring.tiny.spring.ioc.version4.io.ResourceLoader;
import example.spring.tiny.spring.ioc.version4.xml.XmlBeanDefinitionReader;
import org.junit.Test;

import java.util.Map;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {
        // 1.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        // 完成bean的new
        // 完成属性的new
        // 未完成注入，即只执行了构造函数，但没有set属性
        xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

        // 2.初始化beanFactory
        // 将属性注入bean
        BeanFactory beanFactory = new AutowireCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry: xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }

        // 3.获取bean并使用
        HelloWorldService helloWorldService = (HelloWorldService)beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
