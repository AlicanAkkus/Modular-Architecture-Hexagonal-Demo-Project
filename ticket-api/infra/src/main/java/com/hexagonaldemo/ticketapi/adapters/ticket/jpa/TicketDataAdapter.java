package com.hexagonaldemo.ticketapi.adapters.ticket.jpa;

import com.hexagonaldemo.ticketapi.adapters.event.jpa.repository.MeetupJpaRepository;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity.TicketEntity;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository.TicketJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.common.model.Status;
import com.hexagonaldemo.ticketapi.ticket.command.BuyTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketDataAdapter implements TicketDataPort {

    private final TicketJpaRepository ticketJpaRepository;
    private final MeetupJpaRepository meetupJpaRepository;

    @Override
    public Ticket reserve(BuyTicket buyTicket) {
        var ticketEntity = new TicketEntity();
        ticketEntity.setStatus(Status.ACTIVE);
        ticketEntity.setReserveDate(LocalDateTime.now());
        ticketEntity.setAccountId(buyTicket.getAccountId());
        ticketEntity.setMeetupId(buyTicket.getMeetupId());
        ticketEntity.setPrice(calculateTotalPrice(buyTicket));
        ticketEntity.setCount(buyTicket.getCount());

        return ticketJpaRepository.save(ticketEntity).toModel();
    }

    private BigDecimal calculateTotalPrice(BuyTicket buyTicket) {
        var eventEntity = meetupJpaRepository.findById(buyTicket.getMeetupId())
                .orElseThrow(() -> new TicketApiDataNotFoundException("ticketapi.event.notFound"));

        return eventEntity.getPrice().multiply(BigDecimal.valueOf(buyTicket.getCount()));
    }
}
