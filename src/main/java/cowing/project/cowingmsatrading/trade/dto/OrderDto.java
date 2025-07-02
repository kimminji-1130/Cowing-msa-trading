package cowing.project.cowingmsatrading.trade.dto;


import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;

public interface OrderDto {
    Order toOrder(String username);
}