package example.spring.tiny.spring.ioc.version1;

public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) { this.bean = bean; }

    public Object getBean() { return bean; }
}
