package com.hexagonaldemo.ticketapi.configuration;

import com.hexagonaldemo.ticketapi.adapters.payment.event.PaymentEventKafkaStream;
import com.hexagonaldemo.ticketapi.adapters.reservation.event.ReservationEventKafkaStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
        ReservationEventKafkaStream.class,
        PaymentEventKafkaStream.class
})
public class EventStreamsConfiguration {
}