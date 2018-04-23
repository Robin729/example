import java.util.Collections;
import java.util.concurrent.Future;
import java.util.concurrent.locks.StampedLock;

/**
 * @author weijin
 */
public class Test {

  public int waitStatus;

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      final int k = 1;
      System.out.println(k);
    }
  }

  private double x, y;
  private final StampedLock sl = new StampedLock();
Collections.synchronizedList()
}


package io.vertx.example.grpc.zipkin;

        import com.codahale.metrics.MetricRegistry;
        import com.codahale.metrics.SharedMetricRegistries;
        import com.codahale.metrics.Timer;
        import io.prometheus.client.CollectorRegistry;
        import io.prometheus.client.dropwizard.DropwizardExports;
        import io.prometheus.client.vertx.MetricsHandler;
        import io.vertx.core.AbstractVerticle;
        import io.vertx.core.Future;
        import io.vertx.ext.web.Router;

/**
 * @author shaneing
 */
public class PrometheusVerticle extends AbstractVerticle {

  private final static int METRICS_PORT = 10000;
  private final static String SERVICE = "grpc-zipkin";
  private MetricRegistry metricRegistry = SharedMetricRegistries.getOrCreate("exported");
  private final Timer responses = metricRegistry.timer(MetricRegistry.name(SERVICE, "metrics", "response"));

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    CollectorRegistry.defaultRegistry.register(new DropwizardExports(metricRegistry));
    CollectorRegistry.defaultRegistry.register(new VertxExports());
    CollectorRegistry.defaultRegistry.register(new JVMExports());

    Router router = Router.router(vertx);
    router.get("/metrics").handler(cr -> {
      final Timer.Context context = responses.time();
      new MetricsHandler().handle(cr);
      context.stop();
    });

    System.out.println("listen /metrics on port " + METRICS_PORT);
    vertx.createHttpServer().requestHandler(router::accept).listen(METRICS_PORT);

    vertx.setPeriodic(1_000L, e -> {
      metricRegistry.counter(MetricRegistry.name(SERVICE, "testCount")).inc();
    });
    startFuture.complete();
  }
}