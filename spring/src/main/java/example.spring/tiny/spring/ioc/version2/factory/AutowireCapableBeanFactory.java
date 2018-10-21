package example.spring.tiny.spring.ioc.version2.factory;

import example.spring.tiny.spring.ioc.version2.BeanDefinition;

/**
 * 这个类具有自动装配的作用，即自动组装bean的实现
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 初始化
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) {
        try {
            Object object = beanDefinition.getBeanClass().newInstance();
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
