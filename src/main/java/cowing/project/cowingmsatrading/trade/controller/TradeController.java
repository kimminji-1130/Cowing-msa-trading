package cowing.project.cowingmsatrading.trade.controller;

import cowing.project.cowingmsatrading.config.TokenProvider;
import cowing.project.cowingmsatrading.trade.dto.LimitOrderDto;
import cowing.project.cowingmsatrading.trade.dto.MarketBuyOrderDto;
import cowing.project.cowingmsatrading.trade.dto.MarketSellOrderDto;
import cowing.project.cowingmsatrading.trade.queue.OrderQueue;
import cowing.project.cowingmsatrading.trade.queue.OrderTask;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradeController {

    private final OrderQueue orderQueue;
    private final TokenProvider tokenProvider;

    @PostMapping("/api/v1/orders/market/buy")
    public ResponseEntity<String> buy(@RequestBody MarketBuyOrderDto marketBuyOrderDto, @RequestHeader("Authorization") String authorizationHeader) {
        orderQueue.enqueue(new OrderTask(marketBuyOrderDto, tokenProvider.getUsername(authorizationHeader.replace("Bearer ", ""))));
        return ResponseEntity.ok("처리 완료");
    }

    @PostMapping("/api/v1/orders/market/sell")
    public ResponseEntity<String> sell(@RequestBody MarketSellOrderDto marketSellOrderDto, @RequestHeader("Authorization") String authorizationHeader) {
        orderQueue.enqueue(new OrderTask(marketSellOrderDto, tokenProvider.getUsername(authorizationHeader.replace("Bearer ", ""))));
        return ResponseEntity.ok("처리 완료");
    }

    @PostMapping("/api/v1/orders/limit")
    public ResponseEntity<String> limit(@RequestBody LimitOrderDto limitOrderDto, @RequestHeader("Authorization") String authorizationHeader) {
        orderQueue.enqueue(new OrderTask(limitOrderDto, tokenProvider.getUsername(authorizationHeader.replace("Bearer ", ""))));
        return ResponseEntity.ok("처리 완료");
    }
}
