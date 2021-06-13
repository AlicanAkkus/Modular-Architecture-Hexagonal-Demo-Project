package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.usecase.VoidEmptyUseCaseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service("balanceAdminUseCaseHandler")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "administration.enabled", havingValue = "true")
public class BalanceAdminUseCaseHandler implements VoidEmptyUseCaseHandler {

    private final BalancePort balancePort;

    @Override
    public void handle() {
        balancePort.deleteAll();
    }
}