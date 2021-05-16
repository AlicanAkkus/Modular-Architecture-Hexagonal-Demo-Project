package com.hexagonaldemo.paymentapi.adapters.payment.jpa.repository;

import com.hexagonaldemo.paymentapi.adapters.payment.jpa.entity.PaymentEntity;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

    Payment findByAccountId(Long accountId);
}