package example.spring.tiny.spring.ioc.version2;

public class BeanDefinition {

    // bean的对象
    private Object bean;
    // bean的class
    private Class beanClass;
    // bean的全限定名，即包名
    private String beanClassName;

    public BeanDefinition() {}

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
