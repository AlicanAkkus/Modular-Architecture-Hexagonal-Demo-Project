package com.hexagonaldemo.ticketapi.adapters.meetup.jpa.repository;

import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.entity.MeetupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupJpaRepository extends JpaRepository<MeetupEntity, Long> {
}
