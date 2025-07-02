package cowing.project.cowingmsatrading.trade.queue;

import cowing.project.cowingmsatrading.trade.service.TradeProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OrderQueue 에서 OrderTask 를 실제 TradeProcessor 로 전달하기 위한 래퍼.
 */
@Component
@RequiredArgsConstructor
public class TradeProcessorWrapper {

    private final TradeProcessor tradeProcessor;

    public void process(OrderTask task) {
        tradeProcessor.startTradeExecution(task.getOrderDto(), task.getUsername());
    }
}
