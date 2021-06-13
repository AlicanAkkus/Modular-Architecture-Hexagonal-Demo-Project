package com.hexagonaldemo.paymentapi;

import com.hexagonaldemo.paymentapi.adapters.TicketFakeDataAdapter;
import com.hexagonaldemo.ticketapi.ticket.TicketRetrieveUseCaseHandler;
import com.hexagonaldemo.ticketapi.ticket.usecase.TicketRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRetrieveTest {

    TicketRetrieveUseCaseHandler ticketRetrieveUseCase;

    @BeforeEach
    void setUp() {
        ticketRetrieveUseCase = new TicketRetrieveUseCaseHandler(new TicketFakeDataAdapter());
    }

    @Test
    void should_retrieve_ticket() {
        // given
        TicketRetrieve ticketRetrieve = TicketRetrieve.builder()
                .accountId(2001L)
                .build();

        // when
        var tickets = ticketRetrieveUseCase.handle(ticketRetrieve);

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
        var tickets = ticketRetrieveUseCase.handle(ticketRetrieve);

        // then
        assertThat(tickets).isEmpty();
    }

}
