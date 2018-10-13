package example.effective.java.javadesignpatterns.aggregatormicroservices.aggregatorservice;

public class ProductInformationClientImpl implements ProductionInformationClient{
    @Override
    public String getProductTitle() {
        return "response"; // 获得Http的一个服务，返回一个结果
    }
}
