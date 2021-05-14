package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.payment.event.PaymentNotificationAdapter;
import com.hexagonaldemo.ticketapi.adapters.payment.event.message.PaymentRollbackEvent;
import com.hexagonaldemo.ticketapi.adapters.reservation.event.ReservationNotificationAdapter;
import com.hexagonaldemo.ticketapi.adapters.reservation.event.message.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.common.event.consumer.PaymentRollbackEventKafkaStreamTestConsumer;
import com.hexagonaldemo.ticketapi.common.event.consumer.ReservationEventKafkaStreamTestConsumer;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@IT
public class PaymentNotificationAdapterIT extends AbstractIT {

    @Autowired
    PaymentNotificationAdapter paymentNotificationAdapter;

    @Autowired
    PaymentRollbackEventKafkaStreamTestConsumer paymentRollbackEventKafkaStreamTestConsumer;

    @Test
    void should_send_notification() {
        // given
        Payment payment = Payment.builder()
                .id(1L)
                .accountId(2021L)
                .price(BigDecimal.valueOf(359.97))
                .referenceCode("test ref code")
                .build();

        // when
        paymentNotificationAdapter.publish(payment);

        // then
        paymentRollbackEventKafkaStreamTestConsumer.wait(5, 1);
        List<PaymentRollbackEvent> paymentRollbackEvents = paymentRollbackEventKafkaStreamTestConsumer.popAll();

        assertThat(paymentRollbackEvents).hasSize(1);
        assertThat(paymentRollbackEvents.get(0))
                .returns(1L, from(PaymentRollbackEvent::getId));
    }

}
