package com.hexagonaldemo.ticketapi.reservation.port;

import com.hexagonaldemo.ticketapi.common.event.EventPublisher;
import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;
import org.springframework.stereotype.Component;

@Component
public interface ReservationEventPort extends EventPublisher<TicketReservedEvent> {

    void publish(TicketReservedEvent ticketReservedEvent);
}
