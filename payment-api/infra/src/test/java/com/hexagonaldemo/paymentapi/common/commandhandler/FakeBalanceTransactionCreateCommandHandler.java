package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceTransactionCreateCommandHandler implements CommandHandler<Balance, BalanceTransactionCreate> {

    private Map<Long, Balance> balanceMap = new HashMap<>();

    @Override
    public Balance handle(BalanceTransactionCreate balanceTransactionCreate) {
        if (balanceMap.containsKey(balanceTransactionCreate.getAccountId())) {
            Balance balance = balanceMap.get(balanceTransactionCreate.getAccountId());
            if (balanceTransactionCreate.getType().equals(BalanceTransactionType.WITHDRAW)) balance.setAmount(balance.getAmount().subtract(balanceTransactionCreate.getAmount()));
            else balance.setAmount(balance.getAmount().add(balanceTransactionCreate.getAmount()));
            return balance;
        }
        Balance balance = Balance.builder()
                .id(1L)
                .accountId(balanceTransactionCreate.getAccountId())
                .amount(balanceTransactionCreate.getAmount())
                .createdAt(LocalDateTime.now())
                .build();
        balanceMap.put(balanceTransactionCreate.getAccountId(), balance);
        return balance;
    }
}