package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.TicketDataAdapter;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository.TicketJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.command.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@IT
@Sql(scripts = "classpath:sql/meetups.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/tickets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TicketDataAdapterIT extends AbstractIT {

    @Autowired
    TicketDataAdapter ticketAdapter;

    @Autowired
    TicketJpaRepository ticketJpaRepository;

    @Test
    void should_create_ticket() {
        // given
        CreateTicket createTicket = CreateTicket.builder()
                .accountId(1001L)
                .meetupId(2001L)
                .count(3)
                .referenceCode("test ref code")
                .build();

        // when
        Ticket createdTicket = ticketAdapter.create(createTicket);

        // then
        var ticketEntity = ticketJpaRepository.findById(4L);
        assertThat(ticketEntity).isPresent();
        assertThat(ticketEntity.get().toModel()).isEqualTo(createdTicket);
    }

    @Test
    void should_not_create_ticket_when_meetup_not_found() {
        // given
        CreateTicket createTicket = CreateTicket.builder()
                .accountId(1001L)
                .meetupId(999L)
                .count(3)
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiDataNotFoundException.class)
                .isThrownBy(() -> ticketAdapter.create(createTicket))
                .withMessage("ticketapi.meetup.notFound");
    }

    @Test
    void should_retrieve_ticket() {
        // given
        TicketRetrieve ticketRetrieve = TicketRetrieve.builder()
                .accountId(10L)
                .build();

        // when
        List<Ticket> tickets = ticketAdapter.retrieve(ticketRetrieve);

        // then
        assertThat(tickets).isNotNull().hasSize(3)
                .extracting("accountId", "meetupId", "price")
                .containsExactlyInAnyOrder(
                        tuple(10L, 1001L, BigDecimal.valueOf(100.11)),
                        tuple(10L, 1002L, BigDecimal.valueOf(100.12)),
                        tuple(10L, 1003L, BigDecimal.valueOf(100.13))
                );
    }

    @Test
    void should_not_retrieve_ticket_when_ticket_not_found() {
        // given
        TicketRetrieve ticketRetrieve = TicketRetrieve.builder()
                .accountId(666L)
                .build();

        // when
        List<Ticket> tickets = ticketAdapter.retrieve(ticketRetrieve);

        // then
        assertThat(tickets).isEmpty();
    }

    @Test
    void should_delete_all_tickets() {
        // when
        ticketAdapter.deleteAll();

        // then
        var ticketEntities = ticketJpaRepository.findAll();
        assertThat(ticketEntities).isEmpty();
    }
}
