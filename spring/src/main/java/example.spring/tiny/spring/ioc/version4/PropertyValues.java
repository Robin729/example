package example.spring.tiny.spring.ioc.version4;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValues() { }

    public void addPropertyValue(PropertyValue pv) {
        // TODO:对重复的pv要做判断，待补充
        this.propertyValues.add(pv);
    }

    // TODO：需做保护性拷贝，防止外部修改
    public List<PropertyValue> getPropertyValues() { return propertyValues; }
}
