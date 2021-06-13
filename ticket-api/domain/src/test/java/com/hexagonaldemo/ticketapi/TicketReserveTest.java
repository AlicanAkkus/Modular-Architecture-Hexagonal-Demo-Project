package com.hexagonaldemo.ticketapi;

import com.hexagonaldemo.ticketapi.adapters.*;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.common.util.CurrentTimeFactory;
import com.hexagonaldemo.ticketapi.reservation.TicketReserveCommandHandler;
import com.hexagonaldemo.ticketapi.reservation.command.TicketReserve;
import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class TicketReserveTest {

    TicketReserveCommandHandler ticketReserveCommandHandler;
    PaymentRollbackFakeEventAdapter paymentRollbackNotificationPort = new PaymentRollbackFakeEventAdapter();
    TicketReservedFakeEventAdapter reservationNotificationPort = new TicketReservedFakeEventAdapter();

    @BeforeEach
    void setUp() {
        ticketReserveCommandHandler = new TicketReserveCommandHandler(
                new MeetupFakeDataAdapter(),
                new TicketFakeDataAdapter(),
                new PaymentFakeDataAdapter(),
                new AccountFakeDataAdapter(),
                reservationNotificationPort,
                paymentRollbackNotificationPort);

        paymentRollbackNotificationPort.reset();
        CurrentTimeFactory.reset();
    }

    @Test
    void should_reserve_ticket() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 2, 2, 19, 0, 0);
        CurrentTimeFactory.setCustom(now);

        TicketReserve ticketReserve = TicketReserve.builder()
                .accountId(2001L)
                .count(1)
                .meetupId(1001L)
                .referenceCode("test ref code")
                .build();

        // when
        Ticket ticket = ticketReserveCommandHandler.handle(ticketReserve);

        // then
        assertThat(ticket).isNotNull()
                .returns(1L, from(Ticket::getId))
                .returns(ticketReserve.getAccountId(), from(Ticket::getAccountId))
                .returns(ticketReserve.getCount(), from(Ticket::getCount))
                .returns(ticketReserve.getMeetupId(), from(Ticket::getMeetupId))
                .returns(LocalDateTime.of(2021, 1, 1, 19, 0, 0), from(Ticket::getReserveDate))
                .returns(BigDecimal.valueOf(100.00), from(Ticket::getPrice));

        paymentRollbackNotificationPort.assertNull();
        reservationNotificationPort.assertContains(TicketReservedEvent.builder()
                .id(1L)
                .eventCreatedAt(now)
                .accountId(2001L)
                .price(BigDecimal.valueOf(100.00))
                .meetupId(1001L)
                .count(1)
                .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                .paymentId(3001L)
                .build());
    }

    @Test
    void should_not_reserve_ticket_when_payment_fails() {
        // given
        TicketReserve ticketReserve = TicketReserve.builder()
                .accountId(6661L)
                .count(1)
                .meetupId(1001L)
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiBusinessException.class)
                .isThrownBy(() -> ticketReserveCommandHandler.handle(ticketReserve))
                .withMessage("ticketapi.payment.client.error");
    }

    @Test
    void should_not_reserve_ticket_when_ticket_create_fails() {
        // given
        TicketReserve ticketReserve = TicketReserve.builder()
                .accountId(6662L)
                .count(1)
                .meetupId(1001L)
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiBusinessException.class)
                .isThrownBy(() -> ticketReserveCommandHandler.handle(ticketReserve))
                .withMessage("ticketapi.ticket.cannotBeCreated");
    }
}
