package com.hexagonaldemo.ticketapi.ticket;

import com.hexagonaldemo.ticketapi.common.DomainComponent;
import com.hexagonaldemo.ticketapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import com.hexagonaldemo.ticketapi.ticket.usecase.TicketRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class TicketRetrieveUseCaseHandler implements UseCaseHandler<List<Ticket>, TicketRetrieve> {

    private final TicketPort ticketPort;

    public List<Ticket> handle(TicketRetrieve useCase) {
        return ticketPort.retrieve(useCase);
    }
}
