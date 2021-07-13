package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.ObservableUseCasePublisher;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;

@DomainComponent
public class BalanceTransactionCreateUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<Balance, BalanceTransactionCreate> {

    private final BalancePort balancePort;

    public BalanceTransactionCreateUseCaseHandler(BalancePort balancePort) {
        this.balancePort = balancePort;
        register(BalanceTransactionCreate.class, this);
    }

    @Override
    public Balance handle(BalanceTransactionCreate useCase) {
        var balance = balancePort.retrieve(useCase.getAccountId());
        return balancePort.update(balance, useCase);
    }
}