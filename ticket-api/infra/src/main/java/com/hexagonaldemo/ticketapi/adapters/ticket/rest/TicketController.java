package com.hexagonaldemo.ticketapi.adapters.ticket.rest;

import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.TicketResponse;
import com.hexagonaldemo.ticketapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import com.hexagonaldemo.ticketapi.common.rest.DataResponse;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.ticket.command.RetrieveTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController extends BaseController {

    private final CommandHandler<List<Ticket>, RetrieveTicket> retrieveTicketCommandHandler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<DataResponse<TicketResponse>> retrieveTicket(@RequestParam Long accountId) {
        List<Ticket> retrieveTickets = retrieveTicketCommandHandler.handle(toCommand(accountId));
        return respond(toResponse(retrieveTickets));
    }

    private List<TicketResponse> toResponse(List<Ticket> retrieveTickets) {
        return retrieveTickets.stream().map(TicketResponse::from).collect(Collectors.toList());
    }

    private RetrieveTicket toCommand(Long accountId) {
        return RetrieveTicket.builder().accountId(accountId).build();
    }
}
