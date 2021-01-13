package com.hexagonaldemo.ticketapi.adapters.ticket.jpa;

import com.hexagonaldemo.ticketapi.adapters.event.jpa.entity.EventEntity;
import com.hexagonaldemo.ticketapi.adapters.event.jpa.EventJpaRepository;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity.TicketEntity;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository.TicketJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.common.model.Status;
import com.hexagonaldemo.ticketapi.ticket.command.BuyTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketJpaAdapter implements TicketPort {

    private final TicketJpaRepository ticketJpaRepository;
    private final EventJpaRepository eventJpaRepository;

    @Override
    public Ticket buy(BuyTicket buyTicket) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setStatus(Status.ACTIVE);
        ticketEntity.setBoughtDate(LocalDateTime.now());
        ticketEntity.setAccountId(buyTicket.getAccountId());
        ticketEntity.setEventId(buyTicket.getEventId());
        ticketEntity.setPrice(calculateTotalPrice(buyTicket));
        ticketEntity.setCount(buyTicket.getCount());

        return ticketJpaRepository.save(ticketEntity).toModel();
    }

    private BigDecimal calculateTotalPrice(BuyTicket buyTicket) {
        EventEntity eventEntity = eventJpaRepository.findById(buyTicket.getEventId())
                .orElseThrow(() -> new TicketApiDataNotFoundException("ticket-api.event.notFound"));

        return eventEntity.getPrice().multiply(BigDecimal.valueOf(buyTicket.getCount()));
    }
}
