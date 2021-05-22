package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.commandhandler.VoidEmptyCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service("balanceAdminCommandHandler")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "administration.enabled", havingValue = "true")
public class BalanceAdminCommandHandler implements VoidEmptyCommandHandler {

    private final BalancePort balancePort;

    @Override
    public void handle() {
        balancePort.deleteAll();
    }
}