package cowing.project.cowingmsatrading.trade.service;

import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Trade;
import cowing.project.cowingmsatrading.trade.domain.repository.OrderRepository;
import cowing.project.cowingmsatrading.trade.domain.repository.TradeRepository;
import cowing.project.cowingmsatrading.trade.dto.PendingOrderResponse;
import cowing.project.cowingmsatrading.trade.dto.TradeHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final TradeRepository tradeRepository;
    private final OrderRepository orderRepository;

    public List<TradeHistoryResponse> getTradeHistories(String username) {
        List<Trade> trades = tradeRepository.findAllByUsername(username);
        return trades.stream()
                .map(TradeHistoryResponse::of)
                .toList();
    }

    public List<PendingOrderResponse> getPendingOrders(String username) {
        List<Order> orders = orderRepository.findAllByUsernameAndStatus(username, Status.PENDING);
        return orders.stream()
                .map(PendingOrderResponse::of)
                .toList();
    }
}

