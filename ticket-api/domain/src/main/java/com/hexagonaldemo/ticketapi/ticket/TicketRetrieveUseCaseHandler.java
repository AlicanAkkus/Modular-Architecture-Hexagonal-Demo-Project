package com.hexagonaldemo.ticketapi.ticket;

import com.hexagonaldemo.ticketapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.ticketapi.ticket.usecase.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "true")
public class TicketRetrieveUseCaseHandler implements UseCaseHandler<List<Ticket>, TicketRetrieve> {

    private final TicketPort ticketPort;

    public List<Ticket> handle(TicketRetrieve useCase) {
        return ticketPort.retrieve(useCase);
    }
}
