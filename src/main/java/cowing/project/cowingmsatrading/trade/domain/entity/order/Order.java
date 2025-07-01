package cowing.project.cowingmsatrading.trade.domain.entity.order;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "order_history")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, updatable = false)
    private String uuid;

    @Column(nullable = false)
    private String marketCode;

    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderPosition orderPosition;

    // 지정가 주문용 주문 금액 변수
    @Column(nullable = false, updatable = false)
    private Long orderPrice;

    // 시장가 주문용 총 금액 변수
    @Column(nullable = false, updatable = false)
    private Long totalPrice;

    @Column(nullable = false, updatable = false, precision = 32,scale = 8)
    private BigDecimal totalQuantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderRequestedAt;

    @Setter(AccessLevel.PUBLIC)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @PrePersist
    public void prePersist() {
        this.uuid = this.uuid == null ? UUID.randomUUID().toString() : this.uuid;
    }
}
