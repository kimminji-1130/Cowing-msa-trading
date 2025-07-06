//package cowing.project.cowingmsatrading.orderbook;
//
//import cowing.project.cowingmsatrading.orderbook.vo.OrderbookUnitVo;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.redis.core.DefaultTypedTuple;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//
//import java.util.*;
//
//@ExtendWith(MockitoExtension.class)
//class RealTimeOrderbookTest {
//
//    @Mock
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Mock
//    private ZSetOperations<String, String> zSetOperations;
//
//    private RealTimeOrderbook orderbook;
//
//    @BeforeEach
//    void setUp() {
//        Mockito.when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
//        orderbook = new RealTimeOrderbook(redisTemplate);
//    }
//
//    @Test
//    @DisplayName("매도 호가 조회, 목록을 오름차순으로 반환한다.")
//    void testGetAskOrderbook() {
//        String code = "KRW-BTC";
//        String asksKey = "orderbook:" + code + ":asks";
//        Set<ZSetOperations.TypedTuple<String>> tuples = new LinkedHashSet<>();
//        tuples.add(new DefaultTypedTuple<>("2.0", 100.0));
//        tuples.add(new DefaultTypedTuple<>("3.0", 200.0));
//        Mockito.when(zSetOperations.rangeWithScores(asksKey, 0, -1)).thenReturn(tuples);
//
//        List<OrderbookUnitVo> result = orderbook.getAskOrderbook(code);
//
//        Assertions.assertEquals(2, result.size());
//        Assertions.assertEquals(100.0, result.get(0).price());
//        Assertions.assertEquals(2.0, result.get(0).size());
//        Assertions.assertEquals(200.0, result.get(1).price());
//        Assertions.assertEquals(3.0, result.get(1).size());
//    }
//
//    @Test
//    @DisplayName("매도 호가 조회, 빈 목록일 경우 예외를 발생시킨다.")
//    void testGetAskOrderbookEmpty() {
//        String code = "KRW-BTC";
//        Mockito.when(zSetOperations.rangeWithScores("orderbook:" + code + ":asks", 0, -1))
//               .thenReturn(Collections.emptySet());
//        Assertions.assertThrows(IllegalArgumentException.class, () -> orderbook.getAskOrderbook(code));
//    }
//
//    @Test
//    @DisplayName("매수 호가 조회, 목록을 내림차순으로 반환한다.")
//    void testGetBidOrderbook() {
//        String code = "KRW-BTC";
//        String bidsKey = "orderbook:" + code + ":bids";
//        Set<ZSetOperations.TypedTuple<String>> tuples = new LinkedHashSet<>();
//        tuples.add(new DefaultTypedTuple<>("5.0", 300.0));
//        tuples.add(new DefaultTypedTuple<>("4.0", 400.0));
//        Mockito.when(zSetOperations.reverseRangeWithScores(bidsKey, 0, -1)).thenReturn(tuples);
//
//        List<OrderbookUnitVo> result = orderbook.getBidOrderbook(code);
//
//        Assertions.assertEquals(2, result.size());
//        Assertions.assertEquals(300.0, result.get(0).price());
//        Assertions.assertEquals(5.0, result.get(0).size());
//        Assertions.assertEquals(400.0, result.get(1).price());
//        Assertions.assertEquals(4.0, result.get(1).size());
//    }
//
//    @Test
//    @DisplayName("매수 호가 조회, 빈 목록일 경우 예외를 발생시킨다.")
//    void testGetBidOrderbookEmpty() {
//        String code = "KRW-BTC";
//        Mockito.when(zSetOperations.reverseRangeWithScores("orderbook:" + code + ":bids", 0, -1))
//               .thenReturn(Collections.emptySet());
//        Assertions.assertThrows(IllegalArgumentException.class, () -> orderbook.getBidOrderbook(code));
//    }
//}
