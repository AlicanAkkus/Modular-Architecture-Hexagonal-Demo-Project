package com.hexagonaldemo.ticketapi.contract;

import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BaseTicketContract extends AbstractContractTest {

    @Override
    void setUp() {
        when(ticketReserveCommandHandler.handle(any())).thenReturn(buildTicket());
    }

    protected void emitTicketReservedEvent() {
        TicketReservedEvent ticketReservedEvent = TicketReservedEvent.builder()
                .id(300L)
                .accountId(232L)
                .meetupId(342L)
                .reserveDate(LocalDateTime.of(2021, 5, 29, 15, 15, 0))
                .price(BigDecimal.valueOf(60))
                .count(2)
                .paymentId(3221L)
                .eventCreatedAt(LocalDateTime.of(2021, 5, 29, 15, 15, 2))
                .build();

        ticketReservedEventPort.publish(ticketReservedEvent);
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
