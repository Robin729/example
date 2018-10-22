package example.spring.tiny.spring.ioc.version6.context;

import example.spring.tiny.spring.ioc.version6.beans.factory.AbstractBeanFactory;

public class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) { this.beanFactory = beanFactory; }

    public void refresh() throws Exception {

    }

    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }
}
