package cowing.project.cowingmsatrading.trade.domain.repository;

import cowing.project.cowingmsatrading.trade.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
