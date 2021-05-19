package com.hexagonaldemo.ticketapi.ticket.port;

import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.command.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;

import java.util.List;

public interface TicketPort {

    Ticket create(CreateTicket reserveTicket);

    List<Ticket> retrieve(TicketRetrieve ticketRetrieve);

    void deleteAll();
}
