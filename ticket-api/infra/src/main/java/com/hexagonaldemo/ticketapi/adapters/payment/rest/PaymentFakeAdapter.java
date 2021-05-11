package com.hexagonaldemo.ticketapi.adapters.payment.rest;

import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentApiPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "adapters.payment.enabled", havingValue = "false", matchIfMissing = true)
public class PaymentFakeAdapter implements PaymentApiPort {

    @Override
    public Payment pay(CreatePayment createPayment) {
        return Payment.builder()
                .id(1L)
                .accountId(createPayment.getAccountId())
                .price(createPayment.getPrice())
                .referenceCode(createPayment.getReferenceCode())
                .build();
    }
}
