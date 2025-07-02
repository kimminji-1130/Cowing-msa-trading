package cowing.project.cowingmsatrading.trade.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "market_code", nullable = false)
    private String marketCode;

    @Setter
    @Getter
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Setter
    @Getter
    @Column(name = "total_cost", nullable = false)
    private Long totalCost;

    @Setter
    @Getter
    @Column(name = "average_cost", nullable = false)
    private Long averageCost;

    @Builder
    public Portfolio(String username, String marketCode, BigDecimal quantity, Long averageCost, Long totalCost) {
        this.username = username;
        this.marketCode = marketCode;
        this.quantity = quantity;
        this.averageCost = averageCost;
        this.totalCost = totalCost;
    }

}
