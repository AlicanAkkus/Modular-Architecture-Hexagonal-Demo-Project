package com.hexagonaldemo.paymentapi.adapters.account.lock;

import com.hexagonaldemo.paymentapi.account.port.AccountLockPort;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.account.lock.enabled", havingValue = "true")
public class AccountLockRedisAdapter implements AccountLockPort {

    private final RedisLockRegistry redisLockRegistry;

    @Override
    public void lock(Long accountId) {
        if (Objects.isNull(accountId) || !redisLockRegistry.obtain(String.valueOf(accountId)).tryLock()) {
            log.error("Could not lock for account {}", accountId);
            throw new PaymentApiBusinessException("paymentapi.account.lockFailed");
        }
        log.info("Acquired lock for account {}", accountId);
    }

    @Override
    public void unlock(Long accountId) {
        try {
            redisLockRegistry.obtain(String.valueOf(accountId)).unlock();
            log.info("Released lock for account {}", accountId);
        } catch (Exception e) {
            // https://github.com/spring-projects/spring-integration/issues/2894
            // if the lock key is expired, it will throw an exception when we unlock the lock.
            // therefore we swallow the exception, no worries
            log.info("Lock is expired for account {}", accountId);
        }
    }
}
