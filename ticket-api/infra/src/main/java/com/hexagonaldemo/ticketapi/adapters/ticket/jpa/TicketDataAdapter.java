package com.hexagonaldemo.ticketapi.adapters.ticket.jpa;

import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.repository.MeetupJpaRepository;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity.TicketEntity;
import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository.TicketJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.common.model.Status;
import com.hexagonaldemo.ticketapi.ticket.usecase.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.usecase.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketDataAdapter implements TicketPort {

    private final TicketJpaRepository ticketJpaRepository;
    private final MeetupJpaRepository meetupJpaRepository;

    @Override
    public Ticket create(CreateTicket createTicket) {
        var ticketEntity = new TicketEntity();
        ticketEntity.setStatus(Status.ACTIVE);
        ticketEntity.setReserveDate(LocalDateTime.now());
        ticketEntity.setAccountId(createTicket.getAccountId());
        ticketEntity.setMeetupId(createTicket.getMeetupId());
        ticketEntity.setPrice(calculateTotalPrice(createTicket));
        ticketEntity.setCount(createTicket.getCount());

        return ticketJpaRepository.save(ticketEntity).toModel();
    }

    @Override
    public List<Ticket> retrieve(TicketRetrieve ticketRetrieve) {
        return ticketJpaRepository.findAllByAccountId(ticketRetrieve.getAccountId()).stream()
                .map(TicketEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        ticketJpaRepository.deleteAll();
    }

    private BigDecimal calculateTotalPrice(CreateTicket createTicket) {
        var eventEntity = meetupJpaRepository.findById(createTicket.getMeetupId())
                .orElseThrow(() -> new TicketApiDataNotFoundException("ticketapi.meetup.notFound"));

        return eventEntity.getPrice().multiply(BigDecimal.valueOf(createTicket.getCount()));
    }
}
