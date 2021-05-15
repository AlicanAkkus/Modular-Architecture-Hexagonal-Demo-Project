package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.TicketDataAdapter;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity.TicketEntity;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository.TicketJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IT
@Sql(scripts = "classpath:sql/meetups.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
        List<TicketEntity> allTickets = ticketJpaRepository.findAll();
        assertThat(allTickets).hasSize(1);
        assertThat(allTickets.get(0).toModel()).isEqualTo(createdTicket);
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
}
