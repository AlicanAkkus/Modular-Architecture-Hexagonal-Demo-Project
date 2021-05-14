package com.hexagonaldemo.ticketapi.common.model;

import java.time.LocalDateTime;

public interface Event {

    LocalDateTime getEventCreatedAt();

    default void setEventCreatedAt(LocalDateTime eventCreatedAt) {
        // do nothing
    }
}
