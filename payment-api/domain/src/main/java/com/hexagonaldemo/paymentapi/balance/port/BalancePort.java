package com.hexagonaldemo.paymentapi.balance.port;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;

public interface BalancePort {

    Balance retrieve(Long accountId);

    Balance update(Balance balance, BalanceTransactionCreate balanceTransactionCreate);
}