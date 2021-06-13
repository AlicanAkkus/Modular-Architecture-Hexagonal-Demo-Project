package com.hexagonaldemo.ticketapi.adapters.payment.rest;

import com.hexagonaldemo.ticketapi.payment.usecase.PaymentCreate;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "adapters.payment.enabled", havingValue = "false", matchIfMissing = true)
public class PaymentNoopAdapter implements PaymentPort {

    public static final long NOOP_ID = 0L;

    @Override
    public Payment pay(PaymentCreate paymentCreate) {
        return Payment.builder()
                .id(NOOP_ID)
                .accountId(paymentCreate.getAccountId())
                .price(paymentCreate.getPrice())
                .referenceCode(paymentCreate.getReferenceCode())
                .build();
    }
}
