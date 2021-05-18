package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.IT;
import com.hexagonaldemo.paymentapi.adapters.payment.jpa.PaymentDataAdapter;
import com.hexagonaldemo.paymentapi.adapters.payment.jpa.repository.PaymentJpaRepository;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@IT
@Sql(scripts = "classpath:sql/payments.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PaymentDataAdapterIT  extends AbstractIT {

    @Autowired
    PaymentDataAdapter paymentDataAdapter;

    @Autowired
    PaymentJpaRepository paymentJpaRepository;

    @Test
    void should_retrieve_payment() {
        // when
        Payment payment = paymentDataAdapter.retrieve(1L);

        // then
        assertThat(payment).isNotNull()
                .returns(1L, from(Payment::getId))
                .returns(PaymentState.SUCCESS, from(Payment::getState));
    }

    @Test
    void should_not_retrieve_payment_when_payment_not_found() {
        // given
        // when
        assertThatExceptionOfType(PaymentApiBusinessException.class)
                .isThrownBy(() -> paymentDataAdapter.retrieve(666L))
                .withMessage("paymentapi.payment.notFound");
    }

    @Test
    void should_create_payment() {
        // given
        PaymentCreate paymentCreate = PaymentCreate.builder()
                .accountId(1002L)
                .price(BigDecimal.valueOf(99.9))
                .referenceCode("test ref code")
                .build();

        // when
        Payment payment = paymentDataAdapter.create(paymentCreate);

        // then
        assertThat(payment).isNotNull()
                .returns(2L, from(Payment::getId))
                .returns(1002L, from(Payment::getAccountId))
                .returns(BigDecimal.valueOf(99.9), from(Payment::getPrice))
                .returns(PaymentState.SUCCESS, from(Payment::getState));
    }

    @Test
    void should_rollback_payment() {
        // given
        PaymentRollback paymentRollback = PaymentRollback.builder()
                .id(1L)
                .build();

        // when
        paymentDataAdapter.rollback(paymentRollback);

        // then
        assertThat(paymentJpaRepository.findById(1L).get().toModel()).isNotNull()
                .returns(1L, from(Payment::getId))
                .returns(1001L, from(Payment::getAccountId))
                .returns(BigDecimal.valueOf(100.11), from(Payment::getPrice))
                .returns(PaymentState.ROLLBACKED, from(Payment::getState));
    }
}
