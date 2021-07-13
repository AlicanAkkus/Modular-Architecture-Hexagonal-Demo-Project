package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.port.LockPort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceCompensate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.common.usecase.ObservableUseCasePublisher;
import com.hexagonaldemo.paymentapi.common.usecase.VoidUseCaseHandler;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentRollback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@DomainComponent
public class PaymentRollbackUseCaseHandler extends ObservableUseCasePublisher implements VoidUseCaseHandler<PaymentRollback> {

    private final LockPort lockPort;
    private final PaymentPort paymentPort;

    public PaymentRollbackUseCaseHandler(LockPort lockPort, PaymentPort paymentPort) {
        this.lockPort = lockPort;
        this.paymentPort = paymentPort;
        register(PaymentRollback.class, this);
    }

    @Override
    @Transactional
    public void handle(PaymentRollback useCase) {
        var payment = paymentPort.retrieve(useCase.getId());
        validatePaymentRollbackIsAllowed(payment);
        lockPort.lock(payment.getAccountId());

        try {
            paymentPort.rollback(useCase);
            publish(BalanceCompensate.from(payment));
            log.info("Payment {} is rollbacked successfully", useCase.getId());
        } catch (Exception e) {
            log.info("Payment {} cannot be rollbacked due to errors", useCase.getId(), e);
        } finally {
            lockPort.unlock(payment.getAccountId());
        }
    }

    private void validatePaymentRollbackIsAllowed(Payment payment) {
        if (Objects.isNull(payment) || !payment.getState().isRollbackable()) {
            throw new PaymentApiBusinessException("paymentapi.payment.notRollbackable");
        }
    }
}