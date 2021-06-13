package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class BalanceTransactionCreateUseCaseHandler implements UseCaseHandler<Balance, BalanceTransactionCreate> {

    private final BalancePort balancePort;

    @Override
    public Balance handle(BalanceTransactionCreate useCase) {
        var balance = balancePort.retrieve(useCase.getAccountId());
        return balancePort.update(balance, useCase);
    }
}