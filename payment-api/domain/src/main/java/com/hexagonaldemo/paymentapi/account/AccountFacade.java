package com.hexagonaldemo.paymentapi.account;

import com.hexagonaldemo.paymentapi.account.port.AccountLockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountLockPort accountLockPort;

    public void makeBusy(Long accountId) {
        accountLockPort.lock(accountId);
    }

    public void makeFree(Long accountId) {
        accountLockPort.unlock(accountId);
    }
}
