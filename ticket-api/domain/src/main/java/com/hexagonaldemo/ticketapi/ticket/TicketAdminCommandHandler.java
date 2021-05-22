package com.hexagonaldemo.ticketapi.ticket;

import com.hexagonaldemo.ticketapi.common.commandhandler.VoidEmptyCommandHandler;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service("retrieveAdminCommandHandler")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "administration.enabled", havingValue = "true")
public class TicketAdminCommandHandler implements VoidEmptyCommandHandler {

    private final TicketPort ticketPort;

    public void handle() {
        ticketPort.deleteAll();
    }
}
