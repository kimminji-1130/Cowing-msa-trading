package cowing.project.cowingmsatrading.trade.domain.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "order_uuid")
    private String orderUuid;

    @Column(nullable = false)
    private String username;

    private String marketCode;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private OrderPosition orderPosition;

    @Column(nullable = false, name = "trade_price")
    private Long tradePrice;

    @Column(nullable = false, name = "trade_quantity", precision = 20, scale = 8)
    private BigDecimal tradeQuantity;

    @Column(nullable = false)
    private LocalDateTime concludedAt;

    @PrePersist
    public void prePersist() {
        this.concludedAt = LocalDateTime.now();
    }

    @Builder
    public Trade(String orderUuid, String username, String marketCode, OrderType orderType, OrderPosition orderPosition, Long tradePrice, BigDecimal tradeQuantity) {
        this.orderUuid = orderUuid;
        this.username = username;
        this.marketCode = marketCode;
        this.orderType = orderType;
        this.orderPosition = orderPosition;
        this.tradePrice = tradePrice;
        this.tradeQuantity = tradeQuantity;
    }
}

