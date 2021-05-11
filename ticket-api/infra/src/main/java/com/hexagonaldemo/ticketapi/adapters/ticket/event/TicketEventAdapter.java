package com.hexagonaldemo.ticketapi.adapters.ticket.event;

import com.hexagonaldemo.ticketapi.adapters.ticket.event.message.TicketCreateEvent;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class TicketEventAdapter implements TicketEventPort {

    private final TicketEventKafkaStream ticketEventKafkaStream;

    @Override
    public void publish(Ticket ticket) {
        TicketCreateEvent ticketCreateEvent = TicketCreateEvent.from(ticket);
        log.info("Sending ticketCreateEvent {}", ticketCreateEvent);

        ticketEventKafkaStream.ticketCreateOutput().send(MessageBuilder.withPayload(ticketCreateEvent).build());
    }
}
