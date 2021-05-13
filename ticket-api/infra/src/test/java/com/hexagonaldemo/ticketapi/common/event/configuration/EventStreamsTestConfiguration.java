package com.hexagonaldemo.ticketapi.common.event.configuration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = KafkaTestStreams.class)
public class EventStreamsTestConfiguration {
}