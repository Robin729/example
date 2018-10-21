package example.spring.tiny.spring.ioc.version3;

/**
 * 用于bean的属性注入
 */
public class PropertyValue {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }

    public Object getValue() { return value; }
}
