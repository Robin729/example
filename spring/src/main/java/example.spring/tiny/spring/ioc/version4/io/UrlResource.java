package example.spring.tiny.spring.ioc.version4.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 这个是对Resource的实现类
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) { this.url = url; }

    /**
     * 这个方法才是真正触发打开文件的操作
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
