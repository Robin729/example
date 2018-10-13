package example.effective.java.javadesignpatterns.dao;

public class CustomException extends Exception {

    // 默认序列化标志，表示序列化，只有每次编译成class的时候版本才算一样
    private static final long serialVersionUID = 1L;

    public CustomException() {}

    public CustomException(String message) { super(message); }

    public CustomException(String message, Throwable throwable) { super(message, throwable); }

}
