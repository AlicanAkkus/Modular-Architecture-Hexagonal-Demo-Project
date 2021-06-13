package com.hexagonaldemo.ticketapi.reservation.port;

import com.hexagonaldemo.ticketapi.common.event.EventPublisher;
import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;

public interface TicketReservedEventPort extends EventPublisher<TicketReservedEvent> {

    void publish(TicketReservedEvent ticketReservedEvent);
}
