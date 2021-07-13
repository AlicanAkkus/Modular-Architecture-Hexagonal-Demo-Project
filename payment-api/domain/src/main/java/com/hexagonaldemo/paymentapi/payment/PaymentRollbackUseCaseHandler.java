package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceCompensate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
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
@RequiredArgsConstructor
public class PaymentRollbackUseCaseHandler implements VoidUseCaseHandler<PaymentRollback> {

    private final VoidUseCaseHandler<BalanceCompensate> balanceCompensateUseCaseHandler;
    private final AccountFacade accountFacade;
    private final PaymentPort paymentPort;

    @Override
    @Transactional
    public void handle(PaymentRollback useCase) {
        var payment = paymentPort.retrieve(useCase.getId());
        validatePaymentRollbackIsAllowed(payment);
        accountFacade.makeBusy(payment.getAccountId());

        try {
            paymentPort.rollback(useCase);
            balanceCompensateUseCaseHandler.handle(BalanceCompensate.from(payment));
            log.info("Payment {} is rollbacked successfully", useCase.getId());
        } catch (Exception e) {
            log.info("Payment {} cannot be rollbacked due to errors", useCase.getId(), e);
        } finally {
            accountFacade.makeFree(payment.getAccountId());
        }
    }

    private void validatePaymentRollbackIsAllowed(Payment payment) {
        if (Objects.isNull(payment) || !payment.getState().isRollbackable()) {
            throw new PaymentApiBusinessException("paymentapi.payment.notRollbackable");
        }
    }
}