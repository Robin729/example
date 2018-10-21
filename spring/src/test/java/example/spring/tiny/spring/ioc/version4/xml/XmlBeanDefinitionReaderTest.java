package example.spring.tiny.spring.ioc.version4.xml;

import example.spring.tiny.spring.ioc.version4.BeanDefinition;
import example.spring.tiny.spring.ioc.version4.io.ResourceLoader;
import org.junit.Test;

import java.util.Map;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void test() throws Exception {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(new ResourceLoader());
        reader.loadBeanDefinitions("tinyioc.xml");
        Map<String, BeanDefinition> registry = reader.getRegistry();
        assert !registry.isEmpty() : "registry is empty";
    }
}
