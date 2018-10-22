package example.spring.tiny.spring.ioc.version5;

import example.spring.tiny.spring.ioc.version5.factory.AbstractBeanFactory;
import example.spring.tiny.spring.ioc.version5.factory.AutowireCapableBeanFactory;
import example.spring.tiny.spring.ioc.version5.io.ResourceLoader;
import example.spring.tiny.spring.ioc.version5.xml.XmlBeanDefinitionReader;
import org.junit.Test;

import java.util.Map;

public class BeanFactoryTest {

    @Test
    public void testLazy() throws Exception {
//        // 1.读取配置
//        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
//        xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
//
//        // 2.初始化BeanFactory并注册bean
//        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
//        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
//            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
//        }
//
//        // 3.获取bean
//        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
//        helloWorldService.helloWorld();
    }
}
