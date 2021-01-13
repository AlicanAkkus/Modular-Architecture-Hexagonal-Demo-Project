package com.hexagonaldemo.ticketapi.adapters.event.jpa;

import com.hexagonaldemo.ticketapi.adapters.event.jpa.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
}
