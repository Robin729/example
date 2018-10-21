package example.spring.tiny.spring.ioc.version4.io;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceLoaderTest {

    @Test
    public void test() throws IOException {
        ResourceLoader resourceLoader = new ResourceLoader();
        // 资源是在包的路径搜索的，所以要tinyioc.xml需要在class的搜索路径。
        Resource resource = resourceLoader.getResource("tinyioc.xml");
        InputStream inputStream = resource.getInputStream();
        assert inputStream != null : "inputStream is null";
    }
}
