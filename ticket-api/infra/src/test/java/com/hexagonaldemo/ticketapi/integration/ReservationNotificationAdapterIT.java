package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.reservation.event.ReservationNotificationAdapter;
import com.hexagonaldemo.ticketapi.adapters.reservation.event.message.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.common.event.consumer.ReservationEventKafkaStreamTestConsumer;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@IT
class ReservationNotificationAdapterIT extends AbstractIT {

    @Autowired
    ReservationNotificationAdapter reservationNotificationAdapter;

    @Autowired
    ReservationEventKafkaStreamTestConsumer reservationEventKafkaStreamTestConsumer;

    @Test
    void should_send_notification() {
        // given
        Ticket createdTicket = Ticket.builder()
                .id(1L)
                .accountId(2021L)
                .count(1)
                .price(BigDecimal.valueOf(359.97))
                .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                .meetupId(100L)
                .build();

        // when
        reservationNotificationAdapter.publish(createdTicket);

        // then
        reservationEventKafkaStreamTestConsumer.wait(5, 1);
        List<TicketReservedEvent> ticketReservedEvents = reservationEventKafkaStreamTestConsumer.popAll();

        assertThat(ticketReservedEvents).hasSize(1)
                .extracting("accountId", "meetupId", "price")
                .containsExactly(
                        tuple(2021L, 100L, BigDecimal.valueOf(359.97))
                );
    }

}
