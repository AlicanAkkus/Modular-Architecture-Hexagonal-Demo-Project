package com.hexagonaldemo.ticketapi.adapters.payment.event;

import com.hexagonaldemo.ticketapi.adapters.payment.event.message.PaymentRollbackEvent;
import com.hexagonaldemo.ticketapi.notification.NotificationPublisher;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class PaymentNotificationAdapter implements NotificationPublisher<Payment> {

    private final PaymentEventKafkaStream paymentEventKafkaStream;

    @Override
    public void publish(Payment payment) {
        var paymentRollbackEvent = PaymentRollbackEvent.from(payment);
        log.info("Sending paymentRollbackEvent {}", paymentRollbackEvent);

        paymentEventKafkaStream.paymentRollbackOutput().send(MessageBuilder.withPayload(paymentRollbackEvent).build());
    }
}
