package com.hexagonaldemo.paymentapi.adapters.balance.jpa.repository;

import com.hexagonaldemo.paymentapi.adapters.balance.jpa.entity.BalanceTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionJpaRepository extends JpaRepository<BalanceTransactionEntity, Long> {
}