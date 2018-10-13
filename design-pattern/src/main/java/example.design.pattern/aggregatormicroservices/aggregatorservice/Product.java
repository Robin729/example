package example.effective.java.javadesignpatterns.aggregatormicroservices.aggregatorservice;

public class Product {

    private String title;

    private int productInventories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProductInventories() {
        return productInventories;
    }

    public void setProductInventories(int productInventories) {
        this.productInventories = productInventories;
    }
}
