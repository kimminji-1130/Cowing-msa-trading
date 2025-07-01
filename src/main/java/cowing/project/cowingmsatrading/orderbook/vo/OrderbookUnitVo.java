package cowing.project.cowingmsatrading.orderbook.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderbookUnitVo(
        @NotNull Double price, // 호가
        @NotNull Double size   // 수량
) {
}
