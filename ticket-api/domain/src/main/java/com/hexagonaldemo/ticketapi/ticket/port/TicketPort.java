package com.hexagonaldemo.ticketapi.ticket.port;

import com.hexagonaldemo.ticketapi.ticket.command.BuyTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;

public interface TicketPort {

    Ticket buy(BuyTicket buyTicket);
}
