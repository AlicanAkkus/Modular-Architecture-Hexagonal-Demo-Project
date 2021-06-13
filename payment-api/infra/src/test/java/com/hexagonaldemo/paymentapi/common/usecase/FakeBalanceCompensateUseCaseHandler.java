package com.hexagonaldemo.paymentapi.common.usecase;

import com.hexagonaldemo.paymentapi.balance.usecase.BalanceCompensate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceCompensateUseCaseHandler implements VoidUseCaseHandler<BalanceCompensate> {

    private static final Long FAILED_COMPENSATE_ACCOUNT_ID = 6661L;

    @Override
    public void handle(BalanceCompensate useCase) {
        if (useCase.getAccountId().equals(FAILED_COMPENSATE_ACCOUNT_ID)) {
            throw new RuntimeException("error");
        }
    }
}