package cowing.project.cowingmsatrading.domain.repository;

import cowing.project.cowingmsatrading.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}