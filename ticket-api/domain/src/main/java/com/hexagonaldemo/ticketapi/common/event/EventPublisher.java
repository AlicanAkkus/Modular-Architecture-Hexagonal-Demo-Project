package com.hexagonaldemo.ticketapi.common.event;

import com.hexagonaldemo.ticketapi.common.model.Event;

public interface EventPublisher<T extends Event> {

    void publish(T event);
}
