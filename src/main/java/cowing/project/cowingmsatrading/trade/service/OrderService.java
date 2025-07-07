package cowing.project.cowingmsatrading.trade.service;

import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Trade;
import cowing.project.cowingmsatrading.trade.domain.entity.user.Portfolio;
import cowing.project.cowingmsatrading.trade.domain.repository.OrderRepository;
import cowing.project.cowingmsatrading.trade.domain.repository.PortfolioRepository;
import cowing.project.cowingmsatrading.trade.domain.repository.TradeRepository;
import cowing.project.cowingmsatrading.trade.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;
    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;


    @Transactional
    public void insertToOrderHistory(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public void modifyOrderStatus(Order order) {
        order.setStatus(Status.COMPLETED);
        orderRepository.save(order);
    }

    @Transactional
    public void updatePortfolio(Order order, BigDecimal totalQuantity, BigDecimal totalPrice) {
        // 포트폴리오를 조회해서 없다면 새로이 생성하고, 있다면 다음 코드부터 총체적인 계산을 실행한다.
        portfolioRepository.findByUsernameAndMarketCode(order.getUsername(), order.getMarketCode())
                .ifPresentOrElse(
                        portfolio -> {
                            // 포트폴리오가 존재할 경우, 해당 포트폴리오를 업데이트한다.
                            if (order.getOrderPosition() == OrderPosition.BUY) {
                                portfolio.setQuantity(portfolio.getQuantity().add(totalQuantity));
                                portfolio.setTotalCost(portfolio.getTotalCost() + totalPrice.longValue());
                                portfolio.setAverageCost(
                                        BigDecimal.valueOf(portfolio.getTotalCost())
                                                .divide(portfolio.getQuantity(), 8, RoundingMode.HALF_UP)
                                                .longValue()
                                ); //BigDecimal을 기준으로 평단가 계산
                            }
                            if( order.getOrderPosition() == OrderPosition.SELL ) {
                                // 매도일 경우
                                portfolio.setQuantity(portfolio.getQuantity().subtract(totalQuantity));
                                portfolio.setTotalCost(portfolio.getTotalCost() - totalPrice.longValue());

                                // 만약 매도 후 수량이 0 이하가 되면 해당 포트폴리오를 삭제한다.
                                if (portfolio.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                                    portfolioRepository.delete(portfolio);
                                    return;
                                }
                            }
                            portfolioRepository.save(portfolio);
                        },
                        () ->
                        portfolioRepository.save(
                                Portfolio.builder()
                                        .username(order.getUsername())
                                        .marketCode(order.getMarketCode())
                                        .quantity(totalQuantity)
                                        .totalCost(totalPrice.longValue())
                                        .averageCost(totalPrice.divide(totalQuantity, 8, RoundingMode.HALF_UP).longValue())
                                        .build()
                        )
                );
    }

    @Transactional
    public void saveTradeHistory(Order order, BigDecimal currentTradeQuantity, BigDecimal currentTradePrice) {
        tradeRepository.save(
                Trade.builder()
                        .orderUuid(order.getUuid())
                        .marketCode(order.getMarketCode())
                        .orderType(order.getOrderType())
                        .orderPosition(order.getOrderPosition())
                        .tradePrice(currentTradePrice.longValue())
                        .tradeQuantity(currentTradeQuantity)
                        .build()
        );
    }

    @Transactional
    public void updateUserAssets(String username, Long totalPrice) {
        // 예시로, 사용자의 자산을 조회하고, 총 금액을 차감하는 방식으로 구현
        userRepository.findByUsername(username).ifPresent(user -> {
            user.updateHoldings(totalPrice);
            userRepository.save(user);
        });
    }
}
