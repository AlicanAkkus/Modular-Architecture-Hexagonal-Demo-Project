package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceTransactionCreateCommandHandler implements CommandHandler<Balance, BalanceTransactionCreate> {

    @Override
    public Balance handle(BalanceTransactionCreate balanceTransactionCreate) {
        return null;
    }
}