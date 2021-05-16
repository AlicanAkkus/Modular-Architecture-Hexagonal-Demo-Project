package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.balance.BalanceTransactionCreateCommandHandler;
import com.hexagonaldemo.paymentapi.balance.command.BalanceCompensate;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.paymentapi.common.commandhandler.VoidCommandHandler;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentRollbackCommandHandler implements VoidCommandHandler<PaymentRollback> {

    private final VoidCommandHandler<BalanceCompensate> balanceCompensateCommandHandler;
    private final AccountFacade accountFacade;
    private final PaymentPort paymentPort;

    @Transactional
    public void handle(PaymentRollback paymentRollback) {
        var payment = paymentPort.retrieve(paymentRollback.getId());
        validatePaymentRollbackIsAllowed(payment);
        accountFacade.makeBusy(payment.getAccountId());

        try {
            paymentPort.rollback(paymentRollback);
            balanceCompensateCommandHandler.handle(BalanceCompensate.from(payment));
            log.info("Payment {} is rollbacked successfully", paymentRollback.getId());
        } catch (Exception e) {
            log.info("Payment {} cannot be rollbacked due to errors", paymentRollback.getId(), e);
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