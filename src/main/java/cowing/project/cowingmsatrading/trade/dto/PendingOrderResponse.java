package cowing.project.cowingmsatrading.trade.dto;

import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderType;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PendingOrderResponse(
        String uuid,
        String marketCode,
        OrderType orderType,
        OrderPosition orderPosition,
        Long orderPrice,
        BigDecimal totalQuantity,
        LocalDateTime orderRequestedAt,
        Status status
) {
    public static PendingOrderResponse of(Order order) {
        return new PendingOrderResponse(
                order.getUuid(),
                order.getMarketCode(),
                order.getOrderType(),
                order.getOrderPosition(),
                order.getOrderPrice(),
                order.getTotalQuantity(),
                order.getOrderRequestedAt(),
                order.getStatus()
        );
    }
}

