package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.command.BalanceCompensate;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.commandhandler.VoidCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "true")
public class BalanceCompensateCommandHandler implements VoidCommandHandler<BalanceCompensate> {

    private final BalancePort balancePort;

    @Override
    public void handle(BalanceCompensate balanceCompensate) {
        var balance = balancePort.retrieve(balanceCompensate.getAccountId());
        balancePort.update(balance, BalanceTransactionCreate.builder()
                .accountId(balanceCompensate.getAccountId())
                .amount(balanceCompensate.getAmount())
                .type(BalanceTransactionType.COMPENSATE)
                .build());
    }
}