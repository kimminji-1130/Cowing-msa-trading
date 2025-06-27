package cowing.project.cowingmsatrading.domain.repository;

import cowing.project.cowingmsatrading.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
