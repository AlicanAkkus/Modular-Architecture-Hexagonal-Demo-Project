package com.hexagonaldemo.paymentapi.common.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = KafkaEventTestStreams.class)
public class EventStreamsTestConfiguration {
}