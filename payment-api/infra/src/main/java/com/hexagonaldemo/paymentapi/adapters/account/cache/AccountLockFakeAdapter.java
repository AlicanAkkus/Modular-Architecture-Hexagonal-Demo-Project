package com.hexagonaldemo.paymentapi.adapters.account.cache;

import com.hexagonaldemo.paymentapi.account.port.AccountLockPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.account.lock.enabled", havingValue = "false", matchIfMissing = true)
public class AccountLockFakeAdapter implements AccountLockPort {

    @Override
    public boolean lock(Long accountId) {
        log.info("Acquired lock for account {}", accountId);
        return true;
    }

    @Override
    public boolean unlock(Long accountId) {
        log.info("Released lock for account {}", accountId);
        return true;
    }
}
