package com.hexagonaldemo.ticketapi.adapters;

import com.hexagonaldemo.ticketapi.account.model.Account;
import com.hexagonaldemo.ticketapi.account.port.AccountPort;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountFakeDataAdapter implements AccountPort {

    @Override
    public Account retrieve(Long id) {
        return Account.builder()
                .id(id)
                .name("test name")
                .surname("test surname")
                .build();
    }
}
