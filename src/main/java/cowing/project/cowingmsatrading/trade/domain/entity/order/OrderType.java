package cowing.project.cowingmsatrading.trade.domain.entity.order;

import lombok.Getter;

@Getter
public enum OrderType {
    MARKET("market"),
    LIMIT("market");

    private final String orderType;

    OrderType(String orderType) {
        this.orderType = orderType;
    }
}