package com.hexagonaldemo.ticketapi.ticket;

import com.hexagonaldemo.ticketapi.common.DomainComponent;
import com.hexagonaldemo.ticketapi.common.usecase.VoidEmptyUseCaseHandler;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class TicketAdminUseCaseHandler implements VoidEmptyUseCaseHandler {

    private final TicketPort ticketPort;

    public void handle() {
        ticketPort.deleteAll();
    }
}
