package example.effective.java.javadesignpatterns.abstractdocument.practice1;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class AbstractDocument implements Document {

    private final Map<String, Object> properties;

    public AbstractDocument(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Void put(String key, Object value) {
        properties.put(key, value);
        return null;
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        Optional<List<Map<String, Object>>> any = Stream
                .of(get(key))
                .filter(el -> el != null)
                .map(el -> (List<Map<String, Object>>)el)
                .findAny();
        return any.isPresent() ? any.get().stream().map(constructor) : Stream.empty();
    }
}
