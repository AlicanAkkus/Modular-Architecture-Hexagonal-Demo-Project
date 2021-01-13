package com.hexagonaldemo.ticketapi.adapters.account;

import com.hexagonaldemo.ticketapi.account.model.Account;
import com.hexagonaldemo.ticketapi.account.port.AccountDataPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "adapters.account.enabled", havingValue = "true", matchIfMissing = true)
public class AccountDataFakeAdapter implements AccountDataPort {

    @Override
    public Account retrieve(Long id) {
        return Account.builder()
                .id(id)
                .name("name-" + id)
                .surname("surname-" + id)
                .build();
    }
}
