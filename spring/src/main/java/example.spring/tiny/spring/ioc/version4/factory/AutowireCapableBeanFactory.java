package example.spring.tiny.spring.ioc.version4.factory;

import example.spring.tiny.spring.ioc.version4.BeanDefinition;
import example.spring.tiny.spring.ioc.version4.PropertyValue;

import java.lang.reflect.Field;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 初始化bean的过程
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 注入属性，也就是装配属性
     * @param bean
     * @param mbd
     * @throws Exception
     */
    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            // 属性的name就是这个bean的name
            Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            // 将属性注入
            declaredField.set(bean, propertyValue.getValue());
        }
    }
}
