package example.spring.tiny.spring.ioc.version2.factory;

import example.spring.tiny.spring.ioc.version2.BeanDefinition;

/**
 * 这个类其实就是个工厂接口，专门定义了注册bean和获取bean
 */
public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
