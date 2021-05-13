package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.reservation.port.ReservationNotificationPort;
import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ReservationNotificationFakeDataAdapter implements ReservationNotificationPort {

    private List<Ticket> events = new ArrayList<>();

    @Override
    public void publish(Ticket ticket) {
        events.add(ticket);
        log.info("[FAKE] ticket is reserved and event is published: {}", ticket);
    }

    public void assertContains(Ticket... ticket) {
        assertThat(events).containsAnyElementsOf(List.of(ticket));
    }

    public void assertNull() {
        assertThat(events).isEmpty();
    }

    public void reset() {
        events = new ArrayList<>();
    }
}
