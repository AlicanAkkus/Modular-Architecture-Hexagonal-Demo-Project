package com.hexagonaldemo.ticketapi.adapters.event.jpa;

import com.hexagonaldemo.ticketapi.adapters.event.jpa.entity.EventEntity;
import com.hexagonaldemo.ticketapi.adapters.event.jpa.repository.MeetupJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetupDataAdapter implements MeetupDataPort {

    private final MeetupJpaRepository meetupJpaRepository;

    @Override
    public Meetup retrieve(Long id) {
        return meetupJpaRepository.findById(id)
                .map(EventEntity::toModel)
                .orElseThrow(() -> new TicketApiDataNotFoundException("ticketapi.event.notFound"));
    }
}
