//package cowing.project.cowingmsatrading.trade;
//
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import cowing.project.cowingmsatrading.config.TokenProvider;
//import cowing.project.cowingmsatrading.trade.dto.LimitOrderDto;
//import cowing.project.cowingmsatrading.trade.dto.MarketBuyOrderDto;
//import cowing.project.cowingmsatrading.trade.dto.MarketSellOrderDto;
//import cowing.project.cowingmsatrading.trade.queue.OrderQueue;
//import cowing.project.cowingmsatrading.trade.queue.OrderTask;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//class TradeTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private OrderQueue orderQueue;
//
//    @MockitoBean
//    private TokenProvider tokenProvider;
//
//    private static final String AUTH_HEADER = "Authorization";
//    private static final String BEARER_TOKEN = "Bearer testAccessToken";
//    private static final String USERNAME = "testuser";
//
//    @BeforeEach
//    void setUp() {
//        // TokenProvider 가 토큰에서 사용자명을 추출하도록 설정
//        given(tokenProvider.getUsername(anyString())).willReturn(USERNAME);
//    }
//
//    @Test
//    @DisplayName("시장가 매수 주문을 처리한다.")
//    void buy() throws Exception {
//        MarketBuyOrderDto dto = new MarketBuyOrderDto("BTC", "buy", 100000, "KRW-BTC", null);
//
//        mockMvc.perform(post("/api/v1/orders/market/buy")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(AUTH_HEADER, BEARER_TOKEN)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("처리 완료"));
//
//        // OrderQueue 에 enqueue 가 호출되었는지 검증
//        verify(orderQueue).enqueue(any(OrderTask.class));
//    }
//
//    @Test
//    @DisplayName("시장가 매도 주문을 처리한다.")
//    void sell() throws Exception {
//        MarketSellOrderDto dto = new MarketSellOrderDto("BTC", "sell", new BigDecimal("0.01"), "KRW-BTC", null);
//
//        mockMvc.perform(post("/api/v1/orders/market/sell")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(AUTH_HEADER, BEARER_TOKEN)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("처리 완료"));
//
//        verify(orderQueue).enqueue(any(OrderTask.class));
//    }
//
//    @Test
//    @DisplayName("지정가 주문을 처리한다.")
//    void limit() throws Exception {
//        LimitOrderDto dto = new LimitOrderDto("KRW-BTC", new BigDecimal("50000000"), "buy", new BigDecimal("0.01"), new BigDecimal("500000"), null);
//
//        mockMvc.perform(post("/api/v1/orders/limit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(AUTH_HEADER, BEARER_TOKEN)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("처리 완료"));
//
//        verify(orderQueue).enqueue(any(OrderTask.class));
//    }
//}
