package com.hexagonaldemo.ticketapi.adapters.payment.event;

import com.hexagonaldemo.ticketapi.common.event.EventPublisher;
import com.hexagonaldemo.ticketapi.payment.event.PaymentRollbackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class PaymentEventAdapter implements EventPublisher<PaymentRollbackEvent> {

    private final PaymentEventKafkaStream paymentEventKafkaStream;

    @Override
    public void publish(PaymentRollbackEvent paymentRollbackEvent) {
        log.info("Sending paymentRollbackEvent {}", paymentRollbackEvent);
        paymentEventKafkaStream.paymentRollbackOutput().send(MessageBuilder.withPayload(paymentRollbackEvent).build());
    }
}
