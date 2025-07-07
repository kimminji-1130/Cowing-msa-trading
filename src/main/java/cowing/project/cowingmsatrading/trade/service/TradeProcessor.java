package cowing.project.cowingmsatrading.trade.service;

import cowing.project.cowingmsatrading.orderbook.RealTimeOrderbook;
import cowing.project.cowingmsatrading.orderbook.vo.OrderbookUnitVo;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.OrderPosition;
import cowing.project.cowingmsatrading.trade.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeProcessor {

    private final OrderService orderService;
    private final RealTimeOrderbook orderbookProvider;

    // 타입과 포지션에 따라 매매 요청을 처리하는 메서드
    public void startTradeExecution(OrderDto orderDto, String username) {

        log.info("매매 체결을 시작합니다. 주문 정보: {}", orderDto);
        //매매 요청을 받아온다.
        Order orderForExecution = orderDto.toOrder(username);

        // 주문을 처리하기 전에 주문 기록에 저장한다.
        orderService.insertToOrderHistory(orderForExecution);

        //요청 타입에 따라 각 메서드로 분기한다.
        switch (orderForExecution.getOrderType()) {
            case MARKET:
                executeMarketOrder(orderForExecution);
                break;
            case LIMIT:
                executeLimitOrder(orderForExecution);
                break;
        }
    }

    /**
     * 시장가 매매 주문 처리
     * @param order 주문 상세 정보
     */
    private void executeMarketOrder(Order order) {

        boolean isBuyOrder = order.getOrderPosition() == OrderPosition.BUY;

        BigDecimal remaining = initializeRemaining(isBuyOrder, order.getTotalPrice(), order.getTotalQuantity());

        // 총 체결 수량과 액
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 현재 체결 수량과 금액
        BigDecimal tradeQuantity;
        BigDecimal tradePrice;

        // 매도 호가를 조회한다.
        List<OrderbookUnitVo> orderbook;

        do {
            orderbook = getOrderbook(order.getOrderPosition(), order.getMarketCode());

            for (OrderbookUnitVo unit : orderbook) {

                BigDecimal currentPrice = BigDecimal.valueOf(unit.price());
                BigDecimal currentSize = BigDecimal.valueOf(unit.size());

                BigDecimal quantityForTrade = getQuantityForTradeFromIsBuyOrder(isBuyOrder, remaining, currentPrice);

                tradeQuantity = calculateTradeQuantity(isBuyOrder, quantityForTrade, remaining, currentSize);
                tradePrice = tradeQuantity.multiply(currentPrice);

                remaining = remaining.subtract(tradePrice);

                // 체결확정으로 처리하여 내역을 기록한다.
                orderService.saveTradeHistory(order, tradeQuantity, tradePrice);

                totalQuantity = totalQuantity.add(tradeQuantity);
                totalPrice = totalPrice.add(tradePrice);

                if (isRemainingZero(remaining)) {
                    break;
                } else if (remaining.compareTo(BigDecimal.ZERO) < 0 || remaining.abs().compareTo(BigDecimal.ZERO) == 0 || remaining.scale() == 3 ||  remaining.scale() > 3) {
                    remaining = BigDecimal.ZERO; // 잔여 금액 또는 수량이 음수로 떨어지면 합리적 허위 범위 내이므로 0으로 설정
                    break;
                }
            }
        } while (!isRemainingZero(remaining));

        // 거래 완료 후 포트폴리오 업데이트
        orderService.updatePortfolio(order, totalQuantity, totalPrice);

        // 주문 상태를 완료로 변경
        orderService.modifyOrderStatus(order);

        // 자산 업데이트
        orderService.updateUserAssets(order.getUsername(), totalPrice.longValue());
    }

    private static BigDecimal getQuantityForTradeFromIsBuyOrder(boolean isBuy, BigDecimal remaining, BigDecimal currentPrice) {
        return isBuy ? remaining.divide(currentPrice, 8, RoundingMode.HALF_UP) : remaining;
    }

    /**
     * 지정가 매매 주문 처리
     * @param order 주문 상세 정보
     */
    private void executeLimitOrder(Order order) {
        // 지정가
        BigDecimal limitPrice = BigDecimal.valueOf(order.getOrderPrice());

        // 남은 주문 수량
        BigDecimal remainingQuantity = order.getTotalQuantity();

        // 현재 체결 수량과 금액
        BigDecimal tradeQuantity;
        BigDecimal tradePrice;

        // 총 체결 수량과 금액
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 매수인지 매도인지 여부
        boolean isBuyOrder = order.getOrderPosition() == OrderPosition.BUY;

        List<OrderbookUnitVo> orderbook;

        // 체결 가능 확인 시 진행
        do {
            orderbook = getOrderbook(order.getOrderPosition(), order.getMarketCode());

            for (OrderbookUnitVo unit : orderbook) {

                // 지정가 제약 조건 확인
                if (!isPriceConditionMet(isBuyOrder, limitPrice, BigDecimal.valueOf(orderbook.getFirst().price()))) {
                    break; // 가격 조건을 만족하지 않으면 더 이상 체결할 수 없음
                }

                BigDecimal currentPrice = BigDecimal.valueOf(unit.price());
                BigDecimal currentSize = BigDecimal.valueOf(unit.size());

                // 체결 수량 계산
                tradeQuantity = remainingQuantity.min(currentSize);

                // 체결 금액 계산
                tradePrice = tradeQuantity.multiply(currentPrice);

                // 남은 수량 업데이트
                remainingQuantity = remainingQuantity.subtract(tradeQuantity);

                // 체결 확정으로 처리하여 내역 기록
                orderService.saveTradeHistory(order, tradeQuantity, tradePrice);

                // 총 체결 수량과 금액 업데이트
                totalQuantity = totalQuantity.add(tradeQuantity);
                totalPrice = totalPrice.add(tradePrice);

                if (isRemainingZero(remainingQuantity)) {
                    break;
                } else if (remainingQuantity.compareTo(BigDecimal.ZERO) < 0 || remainingQuantity.abs().compareTo(BigDecimal.ZERO) == 0 || remainingQuantity.scale() == 3 ||  remainingQuantity.scale() > 3) {
                    remainingQuantity = BigDecimal.ZERO; // 잔여 금액 또는 수량이 음수로 떨어지면 합리적 허위 범위 내이므로 0으로 설정
                    break;
                }
            }
        } while (!isRemainingZero(remainingQuantity));

        // 거래 완료 후 포트폴리오 업데이트
        orderService.updatePortfolio(order, totalQuantity, totalPrice);

        // 주문 상태를 완료로 변경
        orderService.modifyOrderStatus(order);

        // 자산 업데이트
        orderService.updateUserAssets(order.getUsername(), totalPrice.longValue());
    }

    private static boolean isPriceConditionMet(boolean isBuyOrder, BigDecimal limitPrice, BigDecimal currentPrice) {
        return isBuyOrder ?
                limitPrice.compareTo(currentPrice) >= 0 : // 매수: 지정가 >= 매도호가
                limitPrice.compareTo(currentPrice) <= 0; // 매도: 지정가 <= 매수호가
    }


    // 체결 수량을 결정하는 로직을 담당하는 메서드
    private BigDecimal calculateTradeQuantity(boolean isBuy, BigDecimal quantityForTrade, BigDecimal remaining, BigDecimal currentSize) {
        if (isBuy) {
            // 매수일 경우: 내가 살 수 있는 수량(quantityForTrade)과 시장에 나와있는 매도 물량(currentSize) 중 더 적은 쪽으로 체결
            return quantityForTrade.min(currentSize);
        } else { // SELL
            // 매도일 경우: 내가 팔려는 남은 수량(remaining)과 시장의 매수 물량(currentSize) 중 더 적은 쪽으로 체결
            return remaining.min(currentSize);
        }
    }

    private static BigDecimal initializeRemaining(boolean isBuyOrder, Long totalPrice, BigDecimal totalQuantity) {
        return isBuyOrder
                ? BigDecimal.valueOf(totalPrice)
                : totalQuantity;
    }

    private List<OrderbookUnitVo> getOrderbook(OrderPosition orderPosition, String marketCode) {
        return orderPosition == OrderPosition.BUY
                ? orderbookProvider.getAskOrderbook(marketCode)
                : orderbookProvider.getBidOrderbook(marketCode);
    }

    private static boolean isRemainingZero(BigDecimal remaining) {
        return remaining.compareTo(BigDecimal.ZERO) == 0;
    }




}
