package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;

public class BalanceFakeAdapter implements BalancePort {

    private static final Long FAILED_BALANCE_UPDATE_ACCOUNT_ID = 6661L;

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
        if (balance.getAccountId().equals(FAILED_BALANCE_UPDATE_ACCOUNT_ID)) throw new RuntimeException("error");

        updatedBalance.setAmount(updatedBalance.getAmount().add(balanceTransactionCreate.getAmount()));
        return updatedBalance;
    }

    @Override
    public void deleteAll() {
    }
}
