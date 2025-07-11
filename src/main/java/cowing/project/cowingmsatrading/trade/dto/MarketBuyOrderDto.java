package cowing.project.cowingmsatrading.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderType;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "시장가 매수 주문 요청 DTO")
public record MarketBuyOrderDto(
        @Schema(description = "코인 티커", example = "BTC")
        @JsonProperty("coin_ticker") @NotNull @NotBlank String coinTicker,

        @Schema(description = "매매 포지션", example = "buy")
        @JsonProperty("position") @NotNull @NotBlank String position,

        @Schema(description = "총 주문 금액", example = "100000000")
        @JsonProperty("total_price") @NotNull Integer totalPrice,

        @Schema(description = "마켓 코드", example = "KRW-BTC")
        @JsonProperty("market_code") @NotNull @NotBlank String marketCode,

        @Schema(hidden = true)
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