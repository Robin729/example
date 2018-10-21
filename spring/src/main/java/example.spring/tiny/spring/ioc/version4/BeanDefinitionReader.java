package example.spring.tiny.spring.ioc.version4;

/**
 * 从配置文件中读取,只抽象了加载方式
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
