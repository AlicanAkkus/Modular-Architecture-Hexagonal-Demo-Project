package com.hexagonaldemo.paymentapi.balance.port;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;

public interface BalancePort {

    Balance retrieve(Long accountId);

    Balance update(Balance balance, BalanceTransactionCreate balanceTransactionCreate);

    void deleteAll();
}