package example.spring.tiny.spring.ioc.version6.beans.xml;

import example.spring.tiny.spring.ioc.version6.beans.BeanDefinition;
import example.spring.tiny.spring.ioc.version6.beans.io.ResourceLoader;
import org.junit.Test;

import java.util.Map;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void test() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc6.xml");
        Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
        assert registry.size() > 0;
    }
}
