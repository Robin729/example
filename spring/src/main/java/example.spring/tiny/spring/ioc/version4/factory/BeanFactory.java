package example.spring.tiny.spring.ioc.version4.factory;

import example.spring.tiny.spring.ioc.version4.BeanDefinition;

public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;
}
