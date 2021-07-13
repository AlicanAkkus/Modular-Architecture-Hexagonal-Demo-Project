package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceCompensate;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.ObservableUseCasePublisher;
import com.hexagonaldemo.paymentapi.common.usecase.VoidUseCaseHandler;

@DomainComponent
public class BalanceCompensateUseCaseHandler extends ObservableUseCasePublisher implements VoidUseCaseHandler<BalanceCompensate> {

    private final BalancePort balancePort;

    public BalanceCompensateUseCaseHandler(BalancePort balancePort) {
        this.balancePort = balancePort;
        register(BalanceCompensate.class, this);
    }

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