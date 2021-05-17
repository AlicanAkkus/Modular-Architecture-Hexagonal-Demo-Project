package com.hexagonaldemo.ticketapi.common.event.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaEventTestStreams {

    String TICKET_RESERVED_INPUT = "ticketReservedInput";
    String PAYMENT_ROLLBACK_INPUT = "paymentRollbackInput";

    @Input
    MessageChannel ticketReservedInput();

    @Input
    MessageChannel paymentRollbackInput();

}
