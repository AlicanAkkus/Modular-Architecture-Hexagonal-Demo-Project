package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "true")
public class BalanceTransactionCreateCommandHandler implements CommandHandler<Balance, BalanceTransactionCreate> {

    private final BalancePort balancePort;

    @Override
    public Balance handle(BalanceTransactionCreate balanceTransactionCreate) {
        var balance = balancePort.retrieve(balanceTransactionCreate.getAccountId());
        return balancePort.update(balance, balanceTransactionCreate);
    }
}