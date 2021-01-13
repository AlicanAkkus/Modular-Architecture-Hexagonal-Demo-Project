package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalanceDataPort;

public class BalanceFakeDataAdapter implements BalanceDataPort {

    private final Balance retrievedBalance;
    private final Balance updatedBalance;

    public BalanceFakeDataAdapter(Balance retrievedBalance, Balance updatedBalance) {
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
