package com.hexagonaldemo.ticketapi.adapters.account;

import com.hexagonaldemo.ticketapi.account.model.Account;
import com.hexagonaldemo.ticketapi.account.port.AccountPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Real account adapter has not been created yet.
 * todo icini doldur
 */
@Service
@ConditionalOnProperty(name = "adapters.account.enabled", havingValue = "true", matchIfMissing = true)
public class AccountFakeAdapter implements AccountPort {

    @Override
    public Account retrieve(Long id) {
        return Account.builder()
                .id(id)
                .name("name-" + id)
                .surname("surname-" + id)
                .build();
    }
}
