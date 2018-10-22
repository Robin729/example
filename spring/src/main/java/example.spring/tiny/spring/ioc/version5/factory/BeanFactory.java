package example.spring.tiny.spring.ioc.version5.factory;

import example.spring.tiny.spring.ioc.version5.BeanDefinition;

public interface BeanFactory {

    Object getBean(String name) throws Exception;

    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;
}
