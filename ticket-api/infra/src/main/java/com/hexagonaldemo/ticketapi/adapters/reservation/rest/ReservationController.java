package com.hexagonaldemo.ticketapi.adapters.reservation.rest;

import com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto.ReserveTicketRequest;
import com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto.ReserveTicketResponse;
import com.hexagonaldemo.ticketapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.reservation.command.TicketReserve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class ReservationController extends BaseController {

    private final CommandHandler<Ticket, TicketReserve> reserveTicketCommandHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<ReserveTicketResponse> reserveTicket(@Valid @RequestBody ReserveTicketRequest reserveTicketRequest) {
        var reservedTicket = reserveTicketCommandHandler.handle(reserveTicketRequest.toModel());
        return respond(ReserveTicketResponse.fromModel(reservedTicket));
    }
}
