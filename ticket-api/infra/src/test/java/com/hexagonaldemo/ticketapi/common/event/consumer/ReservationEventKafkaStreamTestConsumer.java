package com.hexagonaldemo.ticketapi.common.event.consumer;

import com.hexagonaldemo.ticketapi.adapters.reservation.event.message.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.common.event.configuration.KafkaTestStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationEventKafkaStreamTestConsumer extends AbstractEventKafkaStreamTestConsumer<TicketReservedEvent> {

    @Override
    @StreamListener(KafkaTestStreams.TICKET_RESERVED_INPUT)
    public void consume(@Payload TicketReservedEvent event) {
        consumerInternal(event);
    }
}
