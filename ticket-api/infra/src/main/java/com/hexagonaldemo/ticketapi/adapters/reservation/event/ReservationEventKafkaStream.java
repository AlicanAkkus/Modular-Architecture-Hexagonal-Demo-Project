package com.hexagonaldemo.ticketapi.adapters.reservation.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ReservationEventKafkaStream {

    @Output
    MessageChannel ticketReservedOutput();
}
