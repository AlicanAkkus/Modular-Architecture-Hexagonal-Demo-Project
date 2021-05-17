package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.payment.event.PaymentEventAdapter;
import com.hexagonaldemo.ticketapi.common.event.consumer.PaymentRollbackEventKafkaStreamTestConsumer;
import com.hexagonaldemo.ticketapi.payment.event.PaymentRollbackEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

@IT
class PaymentRollbackEventAdapterIT extends AbstractIT {

    @Autowired
    PaymentEventAdapter paymentNotificationAdapter;

    @Autowired
    PaymentRollbackEventKafkaStreamTestConsumer paymentRollbackEventKafkaStreamTestConsumer;

    @Test
    void should_send_notification() {
        // given
        PaymentRollbackEvent paymentRollbackEvent = PaymentRollbackEvent.builder()
                .id(1L)
                .build();

        // when
        paymentNotificationAdapter.publish(paymentRollbackEvent);

        // then
        paymentRollbackEventKafkaStreamTestConsumer.wait(5, 1);
        List<PaymentRollbackEvent> paymentRollbackEvents = paymentRollbackEventKafkaStreamTestConsumer.popAll();

        assertThat(paymentRollbackEvents).hasSize(1);
        assertThat(paymentRollbackEvents.get(0))
                .returns(1L, from(PaymentRollbackEvent::getId));
    }

}
