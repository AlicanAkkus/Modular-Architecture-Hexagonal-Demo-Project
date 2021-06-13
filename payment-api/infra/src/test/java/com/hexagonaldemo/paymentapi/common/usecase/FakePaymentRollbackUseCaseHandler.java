package com.hexagonaldemo.paymentapi.common.usecase;

import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentRollback;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Getter
@Service
@Primary
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "false", matchIfMissing = true)
public class FakePaymentRollbackUseCaseHandler implements VoidUseCaseHandler<PaymentRollback> {

    private static final Long FAILED_ROLLBACK_PAYMENT_ID = 6661L;

    private PaymentRollback processedPaymentRollback;

    @Override
    public void handle(PaymentRollback useCase) {
         if (useCase.getId().equals(FAILED_ROLLBACK_PAYMENT_ID)) {
            throw new PaymentApiBusinessException("paymentapi.payment.notRollbackable");
        }
        processedPaymentRollback = useCase;
    }
}
