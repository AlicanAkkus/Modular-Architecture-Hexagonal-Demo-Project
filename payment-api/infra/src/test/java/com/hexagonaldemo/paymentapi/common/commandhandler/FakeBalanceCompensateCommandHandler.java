package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.balance.command.BalanceCompensate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceCompensateCommandHandler implements VoidCommandHandler<BalanceCompensate> {

    private static final Long FAILED_COMPENSATE_ACCOUNT_ID = 6661L;

    @Override
    public void handle(BalanceCompensate balanceCompensate) {
        if (balanceCompensate.getAccountId().equals(FAILED_COMPENSATE_ACCOUNT_ID)) {
            throw new RuntimeException("error");
        }
    }
}