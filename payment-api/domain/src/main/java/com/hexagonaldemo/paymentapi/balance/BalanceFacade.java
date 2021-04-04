package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.port.BalanceDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceFacade {

    private final BalanceDataPort balanceDataPort;
    private final BalanceValidator balanceValidator;

    public Balance retrieve(Long accountId) {
        return balanceDataPort.retrieve(accountId);
    }

    public Balance create(BalanceTransactionCreate balanceTransactionCreate) {
        Balance balance = retrieve(balanceTransactionCreate.getAccountId());
        return balanceDataPort.update(balance, balanceTransactionCreate);
    }

    public void validate(BalanceTransactionCreate balanceTransactionCreate) {
        Balance balance = retrieve(balanceTransactionCreate.getAccountId());
        balanceValidator.validate(balance, balanceTransactionCreate);
    }
}