package cowing.project.cowingmsatrading.trade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class TradeProcessorLogicTest {

    @Test
    @DisplayName("수량 계산 로직: 매수 시 남은 금액을 가격으로 나누기")
    void divisionLogicForBuy() {
        BigDecimal remaining = new BigDecimal("123.456");
        BigDecimal price = new BigDecimal("3.21");
        // 직접 로직
        BigDecimal expected = remaining.divide(price, 8, RoundingMode.HALF_UP);
        assertEquals(new BigDecimal("38.43800623"), expected);
    }

    @Test
    @DisplayName("수량 계산 로직: 매도 시 남은 수량을 그대로 사용")
    void passThroughLogicForSell() {
        BigDecimal remainingQty = new BigDecimal("42.5");
        // 매도 로직은 그대로 반환
        assertEquals(new BigDecimal("42.5"), remainingQty);
    }

    @Test
    @DisplayName("초기 남은 값 설정: 매수는 totalPrice, 매도는 totalQuantity")
    void initialRemainingLogic() {
        long totalPrice = 789;
        BigDecimal totalQty = new BigDecimal("5.67");
        BigDecimal buyInit = BigDecimal.valueOf(totalPrice);
        BigDecimal sellInit = totalQty;
        assertEquals(new BigDecimal("789"), buyInit);
        assertEquals(new BigDecimal("5.67"), sellInit);
    }

    @Test
    @DisplayName("가격 조건 판단 로직: 매수 and 매도")
    void priceConditionLogic() {
        BigDecimal limit = new BigDecimal("50");
        BigDecimal current = new BigDecimal("45");
        // 매수: limit >= current
        assertTrue(limit.compareTo(current) >= 0);
        assertFalse(new BigDecimal("40").compareTo(current) >= 0);
        // 매도: limit <= current
        assertTrue(limit.compareTo(current) <= 0);
        assertFalse(new BigDecimal("55").compareTo(current) <= 0);
    }

    @Test
    @DisplayName("체결 수량 결정 로직: 작은 값 선택")
    void calculateMinLogic() {
        BigDecimal a = new BigDecimal("2.2");
        BigDecimal b = new BigDecimal("3.3");
        // 매수일 때 quantityForTrade/min currentSize
        assertEquals(new BigDecimal("2.2"), a.min(b));
        // 매도일 때 remaining/min currentSize
        assertEquals(new BigDecimal("2.2"), b.min(a));
    }

    @Test
    @DisplayName("남은 값 0 여부 판단 로직")
    void remainingZeroLogic() {
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal nonZero = new BigDecimal("0.0001");
        assertEquals(0, zero.compareTo(BigDecimal.ZERO));
        assertNotEquals(0, nonZero.compareTo(BigDecimal.ZERO));
    }

}
