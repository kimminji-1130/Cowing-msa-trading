package cowing.project.cowingmsatrading.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderType;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public record LimitOrderDto(
        @JsonProperty("market_code") @NotNull @NotBlank String marketCode,
        @JsonProperty("order_price") @NotNull @Positive BigDecimal orderPrice,
        @JsonProperty("position") @NotNull @NotBlank String position,
        @JsonProperty("order_quantity") @NotNull @Positive BigDecimal orderQuantity,
        @JsonProperty("total_order_price") @NotNull @Positive BigDecimal totalOrderPrice,
        LocalDateTime orderRequestedAt
) implements OrderDto {
    public LimitOrderDto {
        orderRequestedAt = LocalDateTime.now();
    }

    @Override
    public Order toOrder(String username) {
        return Order.builder()
                .marketCode(marketCode)
                .orderType(OrderType.LIMIT)
                .orderPosition(OrderPosition.valueOf(position.toUpperCase()))
                .totalPrice(totalOrderPrice.longValue())
                .orderPrice(orderPrice.longValue())
                .totalQuantity(orderQuantity)
                .orderRequestedAt(orderRequestedAt)
                .status(Status.PENDING)
                .username(username)
                .build();
    }
}
