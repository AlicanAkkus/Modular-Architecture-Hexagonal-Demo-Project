package com.hexagonaldemo.paymentapi.account.port;

public interface AccountLockPort {

    void lock(Long accountId);

    void unlock(Long accountId);

}
