package cowing.project.cowingmsatrading.trade.domain.repository;

import cowing.project.cowingmsatrading.trade.domain.entity.order.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}