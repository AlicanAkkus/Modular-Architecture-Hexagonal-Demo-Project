package com.hexagonaldemo.ticketapi.adapters.ticket.rest;

import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.BuyTicketRequest;
import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.TicketResponse;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.ticket.TicketFacade;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController extends BaseController {

    private final TicketFacade ticketFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TicketResponse> buy(@Valid @RequestBody BuyTicketRequest buyTicketRequest) {
        Ticket ticket = ticketFacade.buy(buyTicketRequest.toModel());
        return respond(TicketResponse.fromModel(ticket));
    }
}
