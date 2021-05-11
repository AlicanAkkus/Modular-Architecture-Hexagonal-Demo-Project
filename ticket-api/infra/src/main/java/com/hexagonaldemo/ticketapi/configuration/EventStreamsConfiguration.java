package com.hexagonaldemo.ticketapi.configuration;

import com.hexagonaldemo.ticketapi.adapters.ticket.event.TicketEventKafkaStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
        TicketEventKafkaStream.class
})
public class EventStreamsConfiguration {
}