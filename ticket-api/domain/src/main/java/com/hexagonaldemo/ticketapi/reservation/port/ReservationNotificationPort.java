package com.hexagonaldemo.ticketapi.reservation.port;

import com.hexagonaldemo.ticketapi.notification.NotificationPublisher;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public interface ReservationNotificationPort extends NotificationPublisher<Ticket> {

    void publish(Ticket ticket);
}
