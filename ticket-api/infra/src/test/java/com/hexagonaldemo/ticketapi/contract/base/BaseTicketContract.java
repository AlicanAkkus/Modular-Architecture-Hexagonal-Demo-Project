package com.hexagonaldemo.ticketapi.contract.base;

import com.hexagonaldemo.ticketapi.ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BaseTicketContract extends AbstractContractTest {

    @Override
    void setUp() {
        when(ticketFacade.buy(any())).thenReturn(buildTicket());
    }

    private Ticket buildTicket() {
        return Ticket.builder()
                .id(1L)
                .accountId(123L)
                .meetupId(5L)
                .reserveDate(LocalDateTime.of(2020, 1, 1, 12, 12, 12))
                .price(BigDecimal.valueOf(90.00))
                .count(2)
                .build();
    }
}
