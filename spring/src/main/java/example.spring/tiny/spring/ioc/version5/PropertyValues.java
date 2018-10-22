package example.spring.tiny.spring.ioc.version5;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValues() { }

    public void addPropertyValue(PropertyValue pv) {
        // TODO:对重复属性进行校验
        propertyValues.add(pv);
    }

    // TODO:需做保护性拷贝
    public List<PropertyValue> getPropertyValues() { return this.propertyValues; }
}
