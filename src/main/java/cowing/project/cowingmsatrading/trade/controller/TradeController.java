package cowing.project.cowingmsatrading.trade.controller;

import cowing.project.cowingmsatrading.config.TokenProvider;
import cowing.project.cowingmsatrading.trade.dto.LimitOrderDto;
import cowing.project.cowingmsatrading.trade.dto.MarketBuyOrderDto;
import cowing.project.cowingmsatrading.trade.dto.MarketSellOrderDto;
import cowing.project.cowingmsatrading.trade.queue.OrderQueue;
import cowing.project.cowingmsatrading.trade.queue.OrderTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Trade Engine", description = "매매 체결 관련 API")
public class TradeController {

    private final OrderQueue orderQueue;
    private final TokenProvider tokenProvider;

    @Operation(summary = "시장가 매수 주문", description = "시장가로 매수 주문을 처리합니다.")
    @ApiResponse(responseCode = "200", description = "주문이 접수되었습니다.")
    @PostMapping("/api/v1/orders/market/buy")
    public ResponseEntity<String> buy(@RequestBody MarketBuyOrderDto marketBuyOrderDto, @RequestHeader("Authorization") String authorizationHeader) {
        orderQueue.enqueue(new OrderTask(marketBuyOrderDto, tokenProvider.getUsername(authorizationHeader.replace("Bearer ", ""))));
        return ResponseEntity.ok("주문이 접수되었습니다.");
    }

    @Operation(summary = "시장가 매도 주문", description = "시장가로 매도 주문을 처리합니다.")
    @ApiResponse(responseCode = "200", description = "주문이 접수되었습니다.")
    @PostMapping("/api/v1/orders/market/sell")
    public ResponseEntity<String> sell(@RequestBody MarketSellOrderDto marketSellOrderDto, @RequestHeader("Authorization") String authorizationHeader) {
        orderQueue.enqueue(new OrderTask(marketSellOrderDto, tokenProvider.getUsername(authorizationHeader.replace("Bearer ", ""))));
        return ResponseEntity.ok("주문이 접수되었습니다.");
    }

    @Operation(summary = "지정가 주문", description = "지정가 매매로 주문을 처리합니다. 매수와 매도 모두 지원합니다.")
    @ApiResponse(responseCode = "200", description = "주문이 접수되었습니다.")
    @PostMapping("/api/v1/orders/limit")
    public ResponseEntity<String> limit(@RequestBody LimitOrderDto limitOrderDto, @RequestHeader("Authorization") String authorizationHeader) {
        orderQueue.enqueue(new OrderTask(limitOrderDto, tokenProvider.getUsername(authorizationHeader.replace("Bearer ", ""))));
        return ResponseEntity.ok("주문이 접수되었습니다.");
    }
}
