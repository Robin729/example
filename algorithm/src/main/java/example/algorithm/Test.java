package example.algorithm;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class Test {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {
            String url = routingContext.request().absoluteURI();
            System.out.println(url);
            String biaoti;
            String neirong;

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World from Vert.x-Web!");
        });

        server.requestHandler(router::accept).listen(8080);
    }
}
