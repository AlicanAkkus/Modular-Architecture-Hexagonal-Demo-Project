package com.hexagonaldemo.paymentapi.common.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaEventTestStreams {

    @Output
    MessageChannel paymentRollbackOutput();

}
