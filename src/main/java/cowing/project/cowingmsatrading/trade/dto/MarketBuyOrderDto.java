package cowing.project.cowingmsatrading.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderType;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MarketBuyOrderDto(
        @JsonProperty("coin_ticker") @NotNull @NotBlank String coinTicker,
        @JsonProperty("position") @NotNull @NotBlank String position,
        @JsonProperty("total_price") @NotNull Integer totalPrice,
        @JsonProperty("market_code") @NotNull @NotBlank String marketCode,
        LocalDateTime orderRequestedAt
) implements OrderDto {
    public MarketBuyOrderDto {
        orderRequestedAt =LocalDateTime.now();
    }

    @Override
    public Order toOrder(String username) {
        return Order.builder()
                .marketCode(marketCode)
                .orderType(OrderType.MARKET)
                .orderPosition(OrderPosition.BUY)
                .totalQuantity(BigDecimal.ZERO)
                .orderPrice(0L)
                .totalPrice(totalPrice.longValue())
                .orderRequestedAt(orderRequestedAt)
                .status(Status.PENDING)
                .username(username)
                .build();
    }

}