package com.hexagonaldemo.ticketapi.adapters.reservation.event;

import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.common.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class TicketReservedEventAdapter implements EventPublisher<TicketReservedEvent> {

    private final ReservationEventKafkaStream reservationEventKafkaStream;

    @Override
    public void publish(TicketReservedEvent ticketReservedEvent) {
        log.info("Sending ticketReservedEvent {}", ticketReservedEvent);
        reservationEventKafkaStream.ticketReservedOutput().send(MessageBuilder.withPayload(ticketReservedEvent).build());
    }
}
