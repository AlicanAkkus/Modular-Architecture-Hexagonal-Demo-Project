package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.reservation.port.ReservationEventPort;
import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ReservationEventFakeDataAdapter implements ReservationEventPort {

    private List<TicketReservedEvent> events = new ArrayList<>();

    @Override
    public void publish(TicketReservedEvent ticketReservedEvent) {
        events.add(ticketReservedEvent);
        log.info("[FAKE] ticketReservedEvent is published: {}", ticketReservedEvent);
    }

    public void assertContains(TicketReservedEvent... ticketReservedEvents) {
        assertThat(events).containsAnyElementsOf(List.of(ticketReservedEvents));
    }

    public void assertNull() {
        assertThat(events).isEmpty();
    }

    public void reset() {
        events = new ArrayList<>();
    }
}
