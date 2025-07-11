package cowing.project.cowingmsatrading.trade.dto;

import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderType;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Trade;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "체결 내역 응답")
public record TradeHistoryResponse(
        @Schema(description = "마켓 코드", example = "KRW-BTC")
        String marketCode,
        @Schema(description = "주문 타입", example = "LIMIT")
        OrderType orderType,
        @Schema(description = "주문 포지션", example = "buy")
        OrderPosition orderPosition,
        @Schema(description = "체결 가격", example = "50000000")
        Long tradePrice,
        @Schema(description = "체결 수량", example = "0.1")
        BigDecimal tradeQuantity,
        @Schema(description = "체결 시간", example = "2023-10-01T12:00:00")
        LocalDateTime concludedAt
) {
    public static TradeHistoryResponse of(Trade trade) {
        return new TradeHistoryResponse(
                trade.getMarketCode(),
                trade.getOrderType(),
                trade.getOrderPosition(),
                trade.getTradePrice(),
                trade.getTradeQuantity(),
                trade.getConcludedAt()
        );
    }
}
