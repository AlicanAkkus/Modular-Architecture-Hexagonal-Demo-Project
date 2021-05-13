package com.hexagonaldemo.ticketapi.payment.port;

import com.hexagonaldemo.ticketapi.notification.NotificationPublisher;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public interface PaymentRollbackNotificationPort extends NotificationPublisher<Payment> {

    void publish(Payment payment);
}
