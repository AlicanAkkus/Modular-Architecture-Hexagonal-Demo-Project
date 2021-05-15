package com.hexagonaldemo.paymentapi.adapters.balance.jpa.repository;

import com.hexagonaldemo.paymentapi.adapters.balance.jpa.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceJpaRepository extends JpaRepository<BalanceEntity, Long> {

    Optional<BalanceEntity> findByAccountId(Long accountId);
}