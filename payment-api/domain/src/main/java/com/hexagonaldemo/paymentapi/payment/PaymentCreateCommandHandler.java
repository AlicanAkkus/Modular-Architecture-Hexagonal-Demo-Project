package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.balance.BalanceTransactionCreateCommandHandler;
import com.hexagonaldemo.paymentapi.balance.BalanceValidator;
import com.hexagonaldemo.paymentapi.balance.command.BalanceRetrieve;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransaction;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCreateCommandHandler implements CommandHandler<Payment, PaymentCreate> {

    private final CommandHandler<Balance, BalanceTransactionCreate> balanceTransactionCreateCommandHandler;
    private final CommandHandler<Balance, BalanceRetrieve> balanceRetrieveCommandHandler;
    private final AccountFacade accountFacade;
    private final PaymentPort paymentPort;
    private final BalanceValidator balanceValidator;

    @Override
    @Transactional
    public Payment handle(PaymentCreate paymentCreate) {
        accountFacade.makeBusy(paymentCreate.getAccountId());

        var balanceTransactionCreate = buildBalanceTransactionCreate(paymentCreate);
        var balance = balanceRetrieveCommandHandler.handle(BalanceRetrieve.from(paymentCreate.getAccountId()));
        balanceValidator.validate(balance, balanceTransactionCreate);

        var payment = paymentPort.create(paymentCreate);
        balanceTransactionCreateCommandHandler.handle(balanceTransactionCreate);

        accountFacade.makeFree(paymentCreate.getAccountId());
        log.info("Total {} paid from {}", paymentCreate.getPrice(), paymentCreate.getAccountId());
        return payment;
    }

    private BalanceTransactionCreate buildBalanceTransactionCreate(PaymentCreate paymentCreate) {
        return BalanceTransactionCreate.builder()
                .amount(paymentCreate.getPrice())
                .type(BalanceTransactionType.WITHDRAW)
                .accountId(paymentCreate.getAccountId())
                .build();
    }
}