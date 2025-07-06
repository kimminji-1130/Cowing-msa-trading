//package cowing.project.cowingmsatrading.trade;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//
//class TradeProcessorLogicTest {
//
//    @Test
//    @DisplayName("수량 계산: 매수 시 남은 금액을 가격으로 나누기")
//    void divisionLogicForBuy() {
//        //given
//        BigDecimal remaining = new BigDecimal("123.456");
//        BigDecimal price = new BigDecimal("3.21");
//
//        //when
//        BigDecimal expected = remaining.divide(price, 8, RoundingMode.HALF_UP);
//
//        //then
//        Assertions.assertEquals(new BigDecimal("38.45981308"), expected);
//    }
//
//    @Test
//    @DisplayName("수량 계산: 매도 시 남은 수량을 그대로 사용")
//    void passThroughLogicForSell() {
//        //given
//        BigDecimal remainingQty = new BigDecimal("42.5");
//        BigDecimal currentSize = new BigDecimal("10.0");
//
//        //when
//        remainingQty = remainingQty.subtract(currentSize);
//
//        //then
//        Assertions.assertEquals(new BigDecimal("32.5"), remainingQty);
//    }
//
//    @Test
//    @DisplayName("지정가: 체결 수량은 현재 수량과 남은 수량 중 가장 작은 것")
//    void QuantityConditionLogic() {
//        //given
//        BigDecimal remaining = new BigDecimal("50");
//        BigDecimal currentSize = new BigDecimal("45");
//
//        //when
//        BigDecimal quantityForTrade = remaining.min(currentSize);
//
//        //then
//        Assertions.assertEquals(currentSize, quantityForTrade);
//    }
//
//
//    @Test
//    @DisplayName("남은 값: 수량 혹은 금액이 0인 것을 판단")
//    void remainingZeroLogic() {
//        BigDecimal zero = BigDecimal.ZERO;
//        BigDecimal nonZero = new BigDecimal("0.0001");
//        BigDecimal negative = new BigDecimal("-1.000");
//
//        Assertions.assertEquals(0, zero.compareTo(BigDecimal.ZERO));
//        Assertions.assertNotEquals(0, nonZero.compareTo(BigDecimal.ZERO));
//        Assertions.assertTrue(isRemainingZero(negative));
//    }
//
//    private boolean isRemainingZero(BigDecimal remaining) {
//        return remaining.compareTo(BigDecimal.ZERO) == 0 || remaining.compareTo(BigDecimal.ZERO) < 0;
//    }
//}
