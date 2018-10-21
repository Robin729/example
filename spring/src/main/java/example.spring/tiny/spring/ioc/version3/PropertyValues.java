package example.spring.tiny.spring.ioc.version3;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于bean的属性注入
 * 多个属性的容器
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValues(){ }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValues() { return this.propertyValues; }
}
