package com.hexagonaldemo.ticketapi.ticket.port;

import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public interface TicketEventPort {

    void publish(Ticket ticket);
}
