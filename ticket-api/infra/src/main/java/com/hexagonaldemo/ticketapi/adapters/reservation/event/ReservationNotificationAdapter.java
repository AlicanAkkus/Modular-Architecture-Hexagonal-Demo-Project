package com.hexagonaldemo.ticketapi.adapters.reservation.event;

import com.hexagonaldemo.ticketapi.adapters.reservation.event.message.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.notification.NotificationPublisher;
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
public class ReservationNotificationAdapter implements NotificationPublisher<Ticket> {

    private final ReservationEventKafkaStream reservationEventKafkaStream;

    @Override
    public void publish(Ticket ticket) {
        var ticketCreateEvent = TicketReservedEvent.from(ticket);
        log.info("Sending ticketCreateEvent {}", ticketCreateEvent);

        reservationEventKafkaStream.ticketReservedOutput().send(MessageBuilder.withPayload(ticketCreateEvent).build());
    }
}
