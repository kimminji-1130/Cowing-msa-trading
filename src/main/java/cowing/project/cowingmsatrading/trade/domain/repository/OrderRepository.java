package cowing.project.cowingmsatrading.trade.domain.repository;

import cowing.project.cowingmsatrading.trade.domain.entity.order.Order;
import cowing.project.cowingmsatrading.trade.domain.entity.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUsernameAndStatus(String username, Status status);

    Order findOrderByUsername(String username);
}
