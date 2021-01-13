package com.hexagonaldemo.paymentapi.adapters.payment.jpa;

import com.hexagonaldemo.paymentapi.adapters.payment.jpa.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}