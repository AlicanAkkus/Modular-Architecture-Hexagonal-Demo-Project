package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakePaymentRollbackCommandHandler implements VoidCommandHandler<PaymentRollback> {

    private static final Long FAILED_ROLLBACK_PAYMENT_ID = 6661L;

    @Override
    public void handle(PaymentRollback paymentRollback) {
         if (paymentRollback.getId().equals(FAILED_ROLLBACK_PAYMENT_ID)) {
            throw new PaymentApiBusinessException("paymentapi.payment.notRollbackable");
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
