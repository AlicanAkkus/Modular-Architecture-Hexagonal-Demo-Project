package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.balance.BalanceValidator;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceRetrieve;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class PaymentCreateUseCaseHandler implements UseCaseHandler<Payment, PaymentCreate> {

    private final UseCaseHandler<Balance, BalanceTransactionCreate> balanceTransactionCreateUseCaseHandler;
    private final UseCaseHandler<Balance, BalanceRetrieve> balanceRetrieveUseCaseHandler;
    private final AccountFacade accountFacade;
    private final PaymentPort paymentPort;
    private final BalanceValidator balanceValidator;

    @Override
    public Payment handle(PaymentCreate useCase) {
        accountFacade.makeBusy(useCase.getAccountId());
        try {
            var balanceTransactionCreate = buildBalanceTransactionCreate(useCase);
            var balance = balanceRetrieveUseCaseHandler.handle(BalanceRetrieve.from(useCase.getAccountId()));
            balanceValidator.validate(balance, balanceTransactionCreate);

            var payment = paymentPort.create(useCase);
            balanceTransactionCreateUseCaseHandler.handle(balanceTransactionCreate);

            log.info("Total {} paid from {}", useCase.getPrice(), useCase.getAccountId());
            return payment;
        } finally {
            accountFacade.makeFree(useCase.getAccountId());
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