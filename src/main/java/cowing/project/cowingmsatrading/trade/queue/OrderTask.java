package cowing.project.cowingmsatrading.trade.queue;

import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문 처리를 위해 큐에 적재되는 태스크 객체
 */
@AllArgsConstructor
@Getter
public class OrderTask {
    private final Order order;
    private final String username;
}
