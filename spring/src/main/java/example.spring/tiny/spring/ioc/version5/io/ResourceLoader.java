package example.spring.tiny.spring.ioc.version5.io;

import java.net.URL;

public class ResourceLoader {

    // 相当于Resource的工厂
    public Resource getResource(String name) {
        URL url = this.getClass().getClassLoader().getResource(name);
        return new UrlResource(url);
    }
}
