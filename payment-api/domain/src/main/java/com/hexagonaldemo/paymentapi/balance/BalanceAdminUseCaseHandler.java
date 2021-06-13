package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.usecase.VoidEmptyUseCaseHandler;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class BalanceAdminUseCaseHandler implements VoidEmptyUseCaseHandler {

    private final BalancePort balancePort;

    @Override
    public void handle() {
        balancePort.deleteAll();
    }
}