package example.spring.tiny.spring.ioc.version3;

/**
 * 保存了bean对象和其元信息，包括了属性信息
 */
public class BeanDefinition {

    // bean的对象
    private Object bean;
    // bean的class
    private Class beanClass;
    // bean的全限定名，即包名
    private String beanClassName;
    // bean对象所属的属性信息
    private PropertyValues propertyValues;

    public BeanDefinition() {
    }

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

    // 设置bean的className时，也要把对应的class找到。
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
