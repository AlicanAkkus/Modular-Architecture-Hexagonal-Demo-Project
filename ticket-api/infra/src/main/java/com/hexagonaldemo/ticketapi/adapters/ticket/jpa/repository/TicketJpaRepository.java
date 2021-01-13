package com.hexagonaldemo.ticketapi.adapters.ticket.jpa.repository;

import com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {
}
