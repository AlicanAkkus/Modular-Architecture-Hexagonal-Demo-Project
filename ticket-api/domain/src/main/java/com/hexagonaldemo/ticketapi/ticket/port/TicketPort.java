package com.hexagonaldemo.ticketapi.ticket.port;

import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;

public interface TicketPort {

    Ticket create(CreateTicket reserveTicket);
}
