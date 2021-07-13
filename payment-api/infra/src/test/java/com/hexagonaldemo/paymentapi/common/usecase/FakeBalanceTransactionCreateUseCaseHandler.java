package com.hexagonaldemo.paymentapi.common.usecase;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Primary
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceTransactionCreateUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<Balance, BalanceTransactionCreate> {

    private Map<Long, Balance> balanceMap = new HashMap<>();

    public FakeBalanceTransactionCreateUseCaseHandler() {
        register(BalanceTransactionCreate.class, this);
    }

    @Override
    public Balance handle(BalanceTransactionCreate useCase) {
        if (balanceMap.containsKey(useCase.getAccountId())) {
            Balance balance = balanceMap.get(useCase.getAccountId());
            if (useCase.getType().equals(BalanceTransactionType.WITHDRAW)) balance.setAmount(balance.getAmount().subtract(useCase.getAmount()));
            else balance.setAmount(balance.getAmount().add(useCase.getAmount()));
            return balance;
        }
        Balance balance = Balance.builder()
                .id(1L)
                .accountId(useCase.getAccountId())
                .amount(useCase.getAmount())
                .createdAt(LocalDateTime.now())
                .build();
        balanceMap.put(useCase.getAccountId(), balance);
        return balance;
    }
}