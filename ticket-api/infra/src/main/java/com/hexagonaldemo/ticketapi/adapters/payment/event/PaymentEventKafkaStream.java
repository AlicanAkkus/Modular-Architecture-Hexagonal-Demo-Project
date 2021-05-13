package com.hexagonaldemo.ticketapi.adapters.payment.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PaymentEventKafkaStream {

    @Output
    MessageChannel paymentRollbackOutput();
}
