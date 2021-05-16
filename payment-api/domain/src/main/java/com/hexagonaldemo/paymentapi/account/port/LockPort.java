package com.hexagonaldemo.paymentapi.account.port;

public interface LockPort {

    void lock(Long accountId);

    void unlock(Long accountId);

}
