package example.spring.tiny.spring.ioc.version3.factory;

import example.spring.tiny.spring.ioc.version3.BeanDefinition;

public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;
}
