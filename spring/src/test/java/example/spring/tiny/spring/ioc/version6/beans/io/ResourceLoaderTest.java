package example.spring.tiny.spring.ioc.version6.beans.io;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceLoaderTest {

    @Test
    public void test() throws IOException {
        ResourceLoader resourceLoader = new ResourceLoader();
        Resource resource = resourceLoader.getResource("tinyioc6.xml");
        InputStream inputStream = resource.getInputStream();
        assert inputStream != null;
    }
}
