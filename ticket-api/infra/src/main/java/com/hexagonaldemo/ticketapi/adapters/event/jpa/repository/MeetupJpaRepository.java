package com.hexagonaldemo.ticketapi.adapters.event.jpa.repository;

import com.hexagonaldemo.ticketapi.adapters.event.jpa.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupJpaRepository extends JpaRepository<EventEntity, Long> {
}
