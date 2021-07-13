package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceDeleteAll;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.ObservableUseCasePublisher;
import com.hexagonaldemo.paymentapi.common.usecase.VoidUseCaseHandler;

@DomainComponent
public class BalanceAdminUseCaseHandler extends ObservableUseCasePublisher implements VoidUseCaseHandler<BalanceDeleteAll> {

    private final BalancePort balancePort;

    public BalanceAdminUseCaseHandler(BalancePort balancePort) {
        this.balancePort = balancePort;
        register(BalanceDeleteAll.class, this);
    }

    @Override
    public void handle(BalanceDeleteAll useCase) {
        balancePort.deleteAll();
    }
}