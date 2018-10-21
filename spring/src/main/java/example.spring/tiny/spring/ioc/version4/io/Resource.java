package example.spring.tiny.spring.ioc.version4.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 定位内部资源的接口
 */
public interface Resource {

    /**
     * Resource的作用就是抽象出InputStream。
     * 获得InputStream
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
