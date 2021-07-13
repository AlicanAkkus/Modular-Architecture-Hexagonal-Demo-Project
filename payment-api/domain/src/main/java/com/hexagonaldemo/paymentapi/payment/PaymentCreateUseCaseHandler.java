package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.port.LockPort;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.service.BalanceValidator;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceRetrieve;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.ObservableUseCasePublisher;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentCreate;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@DomainComponent
public class PaymentCreateUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<Payment, PaymentCreate> {

    private final LockPort lockPort;
    private final PaymentPort paymentPort;
    private final BalanceValidator balanceValidator;

    public PaymentCreateUseCaseHandler(LockPort lockPort, PaymentPort paymentPort, BalanceValidator balanceValidator) {
        this.lockPort = lockPort;
        this.paymentPort = paymentPort;
        this.balanceValidator = balanceValidator;
        register(PaymentCreate.class, this);
    }

    @Override
    @Transactional
    public Payment handle(PaymentCreate useCase) {
        lockPort.lock(useCase.getAccountId());
        try {
            var balanceTransactionCreate = buildBalanceTransactionCreate(useCase);
            var balance = publish(Balance.class, BalanceRetrieve.from(useCase.getAccountId()));
            balanceValidator.validate(balance, balanceTransactionCreate);

            var payment = paymentPort.create(useCase);
            publish(balanceTransactionCreate);

            log.info("Total {} paid from {}", useCase.getPrice(), useCase.getAccountId());
            return payment;
        } finally {
            lockPort.unlock(useCase.getAccountId());
        }
    }

    private BalanceTransactionCreate buildBalanceTransactionCreate(PaymentCreate paymentCreate) {
        return BalanceTransactionCreate.builder()
                .amount(paymentCreate.getPrice())
                .type(BalanceTransactionType.WITHDRAW)
                .accountId(paymentCreate.getAccountId())
                .build();
    }
}