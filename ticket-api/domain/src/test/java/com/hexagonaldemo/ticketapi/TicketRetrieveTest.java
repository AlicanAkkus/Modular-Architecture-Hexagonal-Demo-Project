package com.hexagonaldemo.ticketapi;

import com.hexagonaldemo.ticketapi.adapters.TicketFakeDataAdapter;
import com.hexagonaldemo.ticketapi.ticket.TicketRetrieveCommandHandler;
import com.hexagonaldemo.ticketapi.ticket.command.TicketRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRetrieveTest {

    TicketRetrieveCommandHandler ticketRetrieveCommandHandler;

    @BeforeEach
    void setUp() {
        ticketRetrieveCommandHandler = new TicketRetrieveCommandHandler(new TicketFakeDataAdapter());
    }

    @Test
    void should_retrieve_ticket() {
        // given
        TicketRetrieve ticketRetrieve = TicketRetrieve.builder()
                .accountId(2001L)
                .build();

        // when
        var tickets = ticketRetrieveCommandHandler.handle(ticketRetrieve);

        // then
        assertThat(tickets).isNotNull().hasSize(3);
    }

    @Test
    void should_retrieve_no_ticket() {
        // given
        TicketRetrieve ticketRetrieve = TicketRetrieve.builder()
                .accountId(6663L)
                .build();

        // when
        var tickets = ticketRetrieveCommandHandler.handle(ticketRetrieve);

        // then
        assertThat(tickets).isEmpty();
    }

}
