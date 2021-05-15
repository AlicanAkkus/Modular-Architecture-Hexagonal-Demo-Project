package com.hexagonaldemo.paymentapi.adapters.payment.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PaymentEventKafkaStream {

    String PAYMENT_ROLLBACK_INPUT = "paymentRollbackInput";

    @Input
    MessageChannel paymentRollbackInput();
}
