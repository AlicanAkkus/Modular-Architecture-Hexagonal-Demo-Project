package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "true")
public class BalanceTransactionCreateUseCaseHandler implements UseCaseHandler<Balance, BalanceTransactionCreate> {

    private final BalancePort balancePort;

    @Override
    public Balance handle(BalanceTransactionCreate useCase) {
        var balance = balancePort.retrieve(useCase.getAccountId());
        return balancePort.update(balance, useCase);
    }
}