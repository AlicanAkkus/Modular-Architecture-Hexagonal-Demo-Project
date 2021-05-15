package com.hexagonaldemo.paymentapi.configuration;

import com.hexagonaldemo.paymentapi.adapters.payment.event.PaymentEventKafkaStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
        PaymentEventKafkaStream.class
})
public class EventStreamsConfiguration {
}