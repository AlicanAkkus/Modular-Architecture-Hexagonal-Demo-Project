package com.hexagonaldemo.paymentapi.account.port;

public interface AccountLockPort {

    boolean lock(Long accountId);

    boolean unlock(Long accountId);

}
