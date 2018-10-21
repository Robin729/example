package example.spring.tiny.spring.ioc.version4.io;

import java.net.URL;

public class ResourceLoader {

    /**
     * 资源加载的接口
     * 给定location，返回Resource
     * @param location
     * @return
     */
    public Resource getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }
}
