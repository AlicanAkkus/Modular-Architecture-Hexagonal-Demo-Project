package com.hexagonaldemo.paymentapi.contract.base;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.payment.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BasePaymentContractTest extends AbstractContractTest {

    @Override
    void setUp() {
        when(balanceRetrieveCommandHandler.handle(any())).thenReturn(Balance.builder()
                .id(1L)
                .accountId(1L)
                .amount(BigDecimal.TEN)
                .createdAt(LocalDateTime.of(2020,3,13,12,11,10))
                .updatedAt(LocalDateTime.of(2020,3,14,13,12,11))
                .build());

        when(paymentCreateCommandHandler.handle(any())).thenReturn(Payment.builder()
                .id(1L)
                .accountId(1L)
                .price(BigDecimal.TEN)
                .referenceCode("ref1")
                .build());
    }
}