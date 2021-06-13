package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceRetrieve;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class BalanceRetrieveUseCaseHandler implements UseCaseHandler<Balance, BalanceRetrieve> {

    private final BalancePort balancePort;

    @Override
    public Balance handle(BalanceRetrieve useCase) {
        return balancePort.retrieve(useCase.getAccountId());
    }
}