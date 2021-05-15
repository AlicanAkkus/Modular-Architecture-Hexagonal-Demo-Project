package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceFacade {

    private final BalancePort balancePort;
    private final BalanceValidator balanceValidator;

    public Balance retrieve(Long accountId) {
        return balancePort.retrieve(accountId);
    }

    public Balance create(BalanceTransactionCreate balanceTransactionCreate) {
        var balance = retrieve(balanceTransactionCreate.getAccountId());
        return balancePort.update(balance, balanceTransactionCreate);
    }

    public void validate(BalanceTransactionCreate balanceTransactionCreate) {
        var balance = retrieve(balanceTransactionCreate.getAccountId());
        balanceValidator.validate(balance, balanceTransactionCreate);
    }
}