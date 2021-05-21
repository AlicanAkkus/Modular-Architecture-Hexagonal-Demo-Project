package com.hexagonaldemo.ticketapi.adapters.ticket.rest;

import com.hexagonaldemo.ticketapi.common.commandhandler.VoidEmptyCommandHandler;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(name = "acceptance-test.enabled", havingValue = "true") // todo administration.enabled
@RequestMapping("/api/v1/tickets")
public class TicketAdminController extends BaseController {

    @Qualifier("retrieveAdminCommandHandler")
    private final VoidEmptyCommandHandler retrieveAdminCommandHandler;

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllTickets() {
        retrieveAdminCommandHandler.handle();
    }
}
