package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceCompensate;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class BalanceCompensateUseCaseHandler implements VoidUseCaseHandler<BalanceCompensate> {

    private final BalancePort balancePort;

    @Override
    public void handle(BalanceCompensate useCase) {
        var balance = balancePort.retrieve(useCase.getAccountId());
        balancePort.update(balance, BalanceTransactionCreate.builder()
                .accountId(useCase.getAccountId())
                .amount(useCase.getAmount())
                .type(BalanceTransactionType.COMPENSATE)
                .build());
    }
}