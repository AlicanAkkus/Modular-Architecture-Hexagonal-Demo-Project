package com.hexagonaldemo.ticketapi.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = TicketEventKafkaTestStream.class)
public class EventStreamsTestConfiguration {
}