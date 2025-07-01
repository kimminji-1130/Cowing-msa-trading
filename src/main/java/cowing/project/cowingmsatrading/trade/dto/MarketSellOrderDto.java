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

public record MarketSellOrderDto(
        @JsonProperty("coin_ticker") @NotNull @NotBlank String coinTicker,
        @JsonProperty("position") @NotNull @NotBlank String position,
        @JsonProperty("total_quantity") @NotNull @Positive BigDecimal totalQuantity,
        @JsonProperty("market_code") @NotNull @NotBlank String marketCode,
        LocalDateTime orderRequestedAt
) implements OrderDto {
    public MarketSellOrderDto {
        orderRequestedAt =LocalDateTime.now();
    }

    @Override
    public Order toOrder(String username) {
        return Order.builder()
                .marketCode(marketCode)
                .orderType(OrderType.MARKET)
                .orderPosition(OrderPosition.SELL)
                .totalQuantity(totalQuantity)
                .totalPrice(0L)
                .orderPrice(0L)
                .orderRequestedAt(orderRequestedAt)
                .status(Status.PENDING)
                .username(username)
                .build();
    }
}