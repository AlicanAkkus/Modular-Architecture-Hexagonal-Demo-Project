package com.hexagonaldemo.ticketapi.unit;

import com.hexagonaldemo.ticketapi.adapters.payment.rest.PaymentNoopAdapter;
import com.hexagonaldemo.ticketapi.payment.usecase.PaymentCreate;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentNoopAdapterTest {

    PaymentNoopAdapter paymentNoopAdapter;

    @BeforeEach
    void doBeforeEach() {
        paymentNoopAdapter = new PaymentNoopAdapter();
    }

    @Test
    void should_call_payment_api_and_nothing_happens() {
        // given
        PaymentCreate paymentCreate = PaymentCreate.builder()
                .accountId(1001L)
                .price(BigDecimal.valueOf(100.0))
                .referenceCode("ref")
                .build();

        Payment expectedPayment = Payment.builder()
                .id(com.hexagonaldemo.ticketapi.adapters.payment.rest.PaymentNoopAdapter.NOOP_ID)
                .accountId(1001L)
                .price(BigDecimal.valueOf(100.0))
                .referenceCode("ref")
                .build();

        // when
        var createdPayment = paymentNoopAdapter.pay(paymentCreate);

        // then
        assertThat(createdPayment).isEqualTo(expectedPayment);
    }
}
