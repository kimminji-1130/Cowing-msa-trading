package cowing.project.cowingmsatrading.trade.dto;

import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderType;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeHistoryResponse(
        String marketCode,
        OrderType orderType,
        OrderPosition orderPosition,
        Long tradePrice,
        BigDecimal tradeQuantity,
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

