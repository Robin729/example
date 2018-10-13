package example.effective.java.javadesignpatterns.aggregatormicroservices.aggregatorservice;

import java.io.IOException;

public class ProductInventoryClientImpl implements ProductionInventoryClient{

    @Override
    public int getProductInventories() {
        return 0;  // 也是一个服务，获得结果

//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet httpGet = new HttpGet("http://localhost:51516/inventories");
//            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
//                response = EntityUtils.toString(httpResponse.getEntity());
//            }
//        } catch (IOException e) {
//            LOGGER.error("Exception caught.", e);
//        }
//        return Integer.parseInt(response);
    }
}
