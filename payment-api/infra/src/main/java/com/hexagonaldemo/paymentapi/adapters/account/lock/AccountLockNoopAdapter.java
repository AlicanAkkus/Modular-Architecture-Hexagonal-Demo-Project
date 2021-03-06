package com.hexagonaldemo.paymentapi.adapters.account.lock;

import com.hexagonaldemo.paymentapi.account.port.LockPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.account.lock.enabled", havingValue = "false", matchIfMissing = true)
public class AccountLockNoopAdapter implements LockPort {

    @Override
    public void lock(Long accountId) {
        log.info("Acquired lock for account {}", accountId);
    }

    @Override
    public void unlock(Long accountId) {
        log.info("Released lock for account {}", accountId);
    }
}
