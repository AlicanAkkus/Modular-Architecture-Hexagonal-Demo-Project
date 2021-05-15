package com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository;

import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity.TicketEntity;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findAllByAccountId(Long accountId);
}
