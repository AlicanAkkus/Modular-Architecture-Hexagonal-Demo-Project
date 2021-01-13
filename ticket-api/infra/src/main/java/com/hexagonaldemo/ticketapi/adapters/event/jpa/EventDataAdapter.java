package com.hexagonaldemo.ticketapi.adapters.event.jpa;

import com.hexagonaldemo.ticketapi.adapters.event.jpa.entity.EventEntity;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.event.model.Event;
import com.hexagonaldemo.ticketapi.event.port.EventDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventDataAdapter implements EventDataPort {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Event retrieve(Long id) {
        return eventJpaRepository.findById(id)
                .map(EventEntity::toModel)
                .orElseThrow(() -> new TicketApiDataNotFoundException("ticket-api.event.notFound"));
    }
}
