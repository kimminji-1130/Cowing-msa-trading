package cowing.project.cowingmsatrading.trade.controller;

import cowing.project.cowingmsatrading.trade.dto.PendingOrderResponse;
import cowing.project.cowingmsatrading.trade.dto.TradeHistoryResponse;
import cowing.project.cowingmsatrading.trade.service.HistoryService;
import cowing.project.cowingmsatrading.trade.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/histories")
public class HistoryController {

    private final HistoryService historyService;
    private final OrderService orderService;

    @GetMapping("/trades")
    public ResponseEntity<List<TradeHistoryResponse>> getTradeHistories(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(historyService.getTradeHistories(orderService.extractUsernameFromToken(authorizationHeader)));
    }

    @GetMapping("/orders/pending")
    public ResponseEntity<List<PendingOrderResponse>> getPendingOrders(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(historyService.getPendingOrders(orderService.extractUsernameFromToken(authorizationHeader)));
    }
}

