package com.hexagonaldemo.ticketapi.event.port;

import com.hexagonaldemo.ticketapi.event.model.Event;

public interface EventDataPort {

    Event retrieve(Long id);
}
