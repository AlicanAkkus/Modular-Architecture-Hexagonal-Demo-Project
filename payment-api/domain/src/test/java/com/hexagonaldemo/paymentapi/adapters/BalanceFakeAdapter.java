package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;

public class BalanceFakeAdapter implements BalancePort {

    private final Balance retrievedBalance;
    private final Balance updatedBalance;

    public BalanceFakeAdapter(Balance retrievedBalance, Balance updatedBalance) {
        this.retrievedBalance = retrievedBalance;
        this.updatedBalance = updatedBalance;
    }

    @Override
    public Balance retrieve(Long accountId) {
        return retrievedBalance;
    }

    @Override
    public Balance update(Balance balance, BalanceTransactionCreate balanceTransactionCreate) {
        return updatedBalance;
    }
}
