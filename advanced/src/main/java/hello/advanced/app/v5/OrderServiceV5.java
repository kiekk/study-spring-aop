package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final TraceTemplate traceTemplate;
    private final OrderRepositoryV5 orderRepositoryV5;

    public OrderServiceV5(LogTrace trace, OrderRepositoryV5 orderRepositoryV5) {
        this.traceTemplate = new TraceTemplate(trace);
        this.orderRepositoryV5 = orderRepositoryV5;
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderService.orderItem()", () -> {
            orderRepositoryV5.save(itemId);
            return null;
        });
    }

}
