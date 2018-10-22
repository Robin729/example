package example.spring.tiny.spring.ioc.version6.context;

import example.spring.tiny.spring.ioc.version6.beans.BeanDefinition;
import example.spring.tiny.spring.ioc.version6.beans.factory.AbstractBeanFactory;
import example.spring.tiny.spring.ioc.version6.beans.factory.AutowireCapableBeanFactory;
import example.spring.tiny.spring.ioc.version6.beans.io.ResourceLoader;
import example.spring.tiny.spring.ioc.version6.beans.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public void refresh() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }
}
