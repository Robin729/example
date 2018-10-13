package example.effective.java.javadesignpatterns.aggregatormicroservices.aggregatorservice;

public class Aggregator {

    private ProductionInformationClient informationClient;

    private ProductionInventoryClient inventoryClient;

    public Product getProduct() {
        Product product = new Product();
        product.setTitle(informationClient.getProductTitle());
        product.setProductInventories(inventoryClient.getProductInventories());
        return product;
    }
}
