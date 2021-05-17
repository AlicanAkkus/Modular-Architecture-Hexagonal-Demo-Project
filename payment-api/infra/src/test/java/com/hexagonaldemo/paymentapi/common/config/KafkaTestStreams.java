package com.hexagonaldemo.paymentapi.common.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaTestStreams {

    String PAYMENT_ROLLBACK_INPUT = "paymentRollbackInput";

    @Input
    MessageChannel paymentRollbackInput();

}
