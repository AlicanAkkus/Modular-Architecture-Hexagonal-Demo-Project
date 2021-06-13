package com.hexagonaldemo.paymentapi.account;

import com.hexagonaldemo.paymentapi.account.port.LockPort;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class AccountFacade {

    private final LockPort lockPort;

    public void makeBusy(Long accountId) {
        lockPort.lock(accountId);
    }

    public void makeFree(Long accountId) {
        lockPort.unlock(accountId);
    }
}
