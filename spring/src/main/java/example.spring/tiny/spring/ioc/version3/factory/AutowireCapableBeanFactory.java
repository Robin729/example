package example.spring.tiny.spring.ioc.version3.factory;

import example.spring.tiny.spring.ioc.version3.BeanDefinition;
import example.spring.tiny.spring.ioc.version3.PropertyValue;

import java.lang.reflect.Field;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        for (PropertyValue propertyValue: beanDefinition.getPropertyValues().getPropertyValues()){
            Field declareField = bean.getClass().getDeclaredField(propertyValue.getName());
            declareField.setAccessible(true);
            declareField.set(bean, propertyValue.getValue());
        }
    }
}
