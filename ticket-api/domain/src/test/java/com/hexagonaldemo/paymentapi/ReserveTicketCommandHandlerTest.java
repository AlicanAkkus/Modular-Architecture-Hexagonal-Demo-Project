package com.hexagonaldemo.paymentapi;

import com.hexagonaldemo.paymentapi.adapters.*;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.reservation.ReserveTicketCommandHandler;
import com.hexagonaldemo.ticketapi.reservation.command.ReserveTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class ReserveTicketCommandHandlerTest {

    ReserveTicketCommandHandler reserveTicketCommandHandler;
    PaymentRollbackFakeDataAdapter paymentRollbackNotificationPort = new PaymentRollbackFakeDataAdapter();
    ReservationNotificationFakeDataAdapter reservationNotificationPort = new ReservationNotificationFakeDataAdapter();

    @BeforeEach
    void setUp() {
        reserveTicketCommandHandler = new ReserveTicketCommandHandler(
                new MeetupFakeDataAdapter(),
                new TicketFakeDataAdapter(),
                new PaymentFakeDataAdapter(),
                new AccountFakeDataAdapter(),
                reservationNotificationPort,
                paymentRollbackNotificationPort);

        paymentRollbackNotificationPort.reset();
    }

    @Test
    void should_reserve_ticket() {
        // given
        ReserveTicket reserveTicket = ReserveTicket.builder()
                .accountId(2001L)
                .count(1)
                .meetupId(1001L)
                .referenceCode("test ref code")
                .build();

        // when
        Ticket ticket = reserveTicketCommandHandler.handle(reserveTicket);

        // then
        assertThat(ticket).isNotNull()
                .returns(1L, from(Ticket::getId))
                .returns(reserveTicket.getAccountId(), from(Ticket::getAccountId))
                .returns(reserveTicket.getCount(), from(Ticket::getCount))
                .returns(reserveTicket.getMeetupId(), from(Ticket::getMeetupId))
                .returns(LocalDateTime.of(2021, 1, 1, 19, 0, 0), from(Ticket::getReserveDate))
                .returns(BigDecimal.valueOf(100.00), from(Ticket::getPrice));

        paymentRollbackNotificationPort.assertNull();
        reservationNotificationPort.assertContains(Ticket.builder()
                .id(1L)
                .accountId(2001L)
                .price(BigDecimal.valueOf(100.00))
                .meetupId(1001L)
                .count(1)
                .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                .build());
    }

    @Test
    void should_not_reserve_ticket_when_payment_fails() {
        // given
        ReserveTicket reserveTicket = ReserveTicket.builder()
                .accountId(6661L)
                .count(1)
                .meetupId(1001L)
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiBusinessException.class)
                .isThrownBy(() -> reserveTicketCommandHandler.handle(reserveTicket))
                .withMessage("ticketapi.payment.client.error");
    }

    @Test
    void should_not_reserve_ticket_when_ticket_create_fails() {
        // given
        ReserveTicket reserveTicket = ReserveTicket.builder()
                .accountId(6662L)
                .count(1)
                .meetupId(1001L)
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiBusinessException.class)
                .isThrownBy(() -> reserveTicketCommandHandler.handle(reserveTicket))
                .withMessage("ticketapi.ticket.cannotBeCreated");
    }
}
