package com.hexagonaldemo.ticketapi.adapters;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.payment.command.PaymentCreate;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PaymentFakeDataAdapter implements PaymentPort {

    private static final Long PAYMENT_FAIL_ACCOUNT_ID = 6661L;
    private static final List<Long> FAILING_IDS = List.of(PAYMENT_FAIL_ACCOUNT_ID);

    @Override
    public Payment pay(PaymentCreate paymentCreate) {
        failedPaymentScenario(paymentCreate);
        return succeededPaymentScenario(paymentCreate);
    }

    private void failedPaymentScenario(PaymentCreate paymentCreate) {
        if (paymentCreate.getAccountId().equals(PAYMENT_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.payment.client.error");
        }
    }

    private Payment succeededPaymentScenario(PaymentCreate paymentCreate) {
        if (!FAILING_IDS.contains(paymentCreate.getAccountId())) {
            return Payment.builder()
                    .id(3001L)
                    .accountId(paymentCreate.getAccountId())
                    .price(paymentCreate.getPrice())
                    .referenceCode(paymentCreate.getReferenceCode())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
