package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PaymentFakeDataAdapter implements PaymentPort {

    private static final Long PAYMENT_FAIL_ACCOUNT_ID = 6661L;
    private static final List<Long> FAILING_IDS = List.of(PAYMENT_FAIL_ACCOUNT_ID);

    @Override
    public Payment pay(CreatePayment createPayment) {
        failedPaymentScenario(createPayment);
        return succeededPaymentScenario(createPayment);
    }

    private void failedPaymentScenario(CreatePayment createPayment) {
        if (createPayment.getAccountId().equals(PAYMENT_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.payment.client.error");
        }
    }

    private Payment succeededPaymentScenario(CreatePayment createPayment) {
        if (!FAILING_IDS.contains(createPayment.getAccountId())) {
            return Payment.builder()
                    .id(3001L)
                    .accountId(createPayment.getAccountId())
                    .price(createPayment.getPrice())
                    .referenceCode(createPayment.getReferenceCode())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
