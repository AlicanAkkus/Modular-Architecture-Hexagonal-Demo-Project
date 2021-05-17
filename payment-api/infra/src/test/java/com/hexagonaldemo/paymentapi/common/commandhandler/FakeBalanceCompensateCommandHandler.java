package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.balance.command.BalanceCompensate;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceCompensateCommandHandler implements VoidCommandHandler<BalanceCompensate> {

    @Override
    public void handle(BalanceCompensate balanceCompensate) {
    }
}