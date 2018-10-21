package example.spring.tiny.spring.ioc.version4.factory;

import example.spring.tiny.spring.ioc.version4.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        // 初始化bean
        Object bean = doCreateBean(beanDefinition);
        // 注册bean
        beanDefinition.setBean(bean);
        beanDefinitionMap.put(name, beanDefinition);
    }

    // 实例化bean的地方
    protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;
}
