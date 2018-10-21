package example.spring.tiny.spring.ioc.version1;

import example.spring.tiny.spring.ioc.version1.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    public Object getBean(String name) { return beanDefinitionMap.get(name).getBean(); }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }
}
