package com.hexagonaldemo.ticketapi.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TicketEventKafkaTestStream {

    String TICKET_CREATE_INPUT = "ticketCreateInput";

    @Input
    MessageChannel ticketCreateInput();
}
