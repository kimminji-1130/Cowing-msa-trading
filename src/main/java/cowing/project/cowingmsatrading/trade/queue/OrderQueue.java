package cowing.project.cowingmsatrading.trade.queue;

import cowing.project.cowingmsatrading.trade.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TradeController 로 들어오는 모든 주문 요청을 처리하기 위한 큐.
 * 단일 스레드로 순차적으로 주문이 처리되도록 보장한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderQueue {

    private final TradeProcessorWrapper tradeProcessorWrapper;
    private final OrderService orderService;

    private final BlockingQueue<OrderTask> queue = new LinkedBlockingQueue<>();
    private ExecutorService executor;

    @PostConstruct
    private void init() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::process); // 백그라운드에서 큐 처리 시작
    }

    @PreDestroy
    private void shutdown() {
        executor.shutdownNow();
    }

    /**
     * 큐에 주문 태스크를 추가한다.
     */
    public void enqueue(OrderTask task) {
        orderService.insertToOrderHistory(task.getOrderDto().toOrder(task.getUsername()));
        queue.add(task);
    }

    // 큐에서 하나씩 꺼내 처리한다.
    private void process() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                OrderTask task = queue.take();
                tradeProcessorWrapper.process(task);
                log.info("{} 를 시작합니다.", task);
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
