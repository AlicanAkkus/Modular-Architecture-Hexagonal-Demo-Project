package com.hexagonaldemo.paymentapi.unit;

import com.hexagonaldemo.paymentapi.adapters.account.lock.AccountLockNoopAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AccountLockNoopAdapterTest {

    AccountLockNoopAdapter accountLockNoopAdapter;

    @BeforeEach
    void doBeforeEach() {
        accountLockNoopAdapter = new AccountLockNoopAdapter();
    }

    @Test
    void should_lock_and_nothing_happens() {
        assertDoesNotThrow(() -> accountLockNoopAdapter.lock(1L));
    }

    @Test
    void should_unlock_and_nothing_happens() {
        assertDoesNotThrow(() -> accountLockNoopAdapter.unlock(1L));
    }

}
