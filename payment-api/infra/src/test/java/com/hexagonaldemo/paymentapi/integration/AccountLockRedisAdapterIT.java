package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.IT;
import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@IT
class AccountLockRedisAdapterIT extends AbstractIT {

    @Autowired
    AccountFacade accountFacade;

    @Autowired
    RedisLockRegistry accountLockRegistry;

    long accountId;

    @BeforeEach
    void doBeforeEach() throws InterruptedException {
        accountId = RandomUtils.nextLong(1000L, 9999L);
        Thread thread = new Thread(() -> accountLockRegistry.expireUnusedOlderThan(0));
        thread.start();
        thread.join();
    }

    @Test
    void should_make_account_busy_and_free() {
        assertDoesNotThrow(() -> accountFacade.makeBusy(accountId));
        assertDoesNotThrow(() -> accountFacade.makeFree(accountId));
        assertDoesNotThrow(() -> accountFacade.makeBusy(accountId));
        assertDoesNotThrow(() -> accountFacade.makeFree(accountId));
    }

    @Test
    void should_make_account_busy_multiple_times_on_same_thread() {
        assertDoesNotThrow(() -> accountFacade.makeBusy(accountId));
        assertDoesNotThrow(() -> accountFacade.makeBusy(accountId));
    }

    @Test
    void should_make_account_free_multiple_times() {
        assertDoesNotThrow(() -> accountFacade.makeFree(accountId));
        assertDoesNotThrow(() -> accountFacade.makeFree(accountId));
    }

    @Test
    void should_not_make_account_busy_with_null_id() {
        assertThatExceptionOfType(PaymentApiBusinessException.class)
                .isThrownBy(() -> accountFacade.makeBusy(null))
                .withMessage("paymentapi.account.lockFailed");
    }

    @Test
    void should_make_account_busy_with_null_id() {
        assertDoesNotThrow(() -> accountFacade.makeFree(null));
    }

    @Test
    void should_make_account_busy_after_retry() throws InterruptedException {
        //given
        LockTask task = new LockTask(accountId, 500L, accountFacade);
        Thread thread = new Thread(task);
        thread.start();

        //when
        assertThatExceptionOfType(PaymentApiBusinessException.class)
                .isThrownBy(() -> accountFacade.makeBusy(accountId))
                .withMessage("paymentapi.account.lockFailed");

        thread.join();
    }

    @Test
    void should_make_account_busy_throw_exception_on_multi_thread_touch() throws InterruptedException {
        //given
        LockTask task = new LockTask(accountId, 0L, accountFacade);
        Thread thread = new Thread(task);
        thread.start();

        //when
        assertDoesNotThrow(() -> accountFacade.makeFree(accountId));

        thread.join();
    }

    private static class LockTask implements Runnable {

        private final AccountFacade accountFacade;
        private final Long id;
        private final Long unlockTime;

        public LockTask(Long id, Long unlockTime, AccountFacade accountFacade) {
            this.id = id;
            this.unlockTime = unlockTime;
            this.accountFacade = accountFacade;
        }

        @SneakyThrows
        @Override
        public void run() {
            accountFacade.makeBusy(id);
            if (unlockTime > 0) {
                Thread.sleep(unlockTime);
                accountFacade.makeFree(id);
            }
        }
    }
}
