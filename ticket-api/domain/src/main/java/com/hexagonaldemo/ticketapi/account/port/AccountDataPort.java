package com.hexagonaldemo.ticketapi.account.port;

import com.hexagonaldemo.ticketapi.account.model.Account;

public interface AccountDataPort {

    Account retrieve(Long id);
}
