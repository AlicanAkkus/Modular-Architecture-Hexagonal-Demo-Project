package com.hexagonaldemo.ticketapi.adapters.payment.rest;

import com.hexagonaldemo.ticketapi.payment.command.PaymentCreate;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "adapters.payment.enabled", havingValue = "false", matchIfMissing = true)
public class PaymentFakeAdapter implements PaymentPort {

    @Override
    public Payment pay(PaymentCreate paymentCreate) {
        return Payment.builder()
                .id(1L)
                .accountId(paymentCreate.getAccountId())
                .price(paymentCreate.getPrice())
                .referenceCode(paymentCreate.getReferenceCode())
                .build();
    }
}
