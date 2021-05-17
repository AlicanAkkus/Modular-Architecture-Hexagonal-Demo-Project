package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.common.EventAssertion;
import com.hexagonaldemo.paymentapi.common.commandhandler.FakePaymentRollbackCommandHandler;
import com.hexagonaldemo.paymentapi.common.consumer.PaymentRollbackEventKafkaStreamTestPublisher;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.event.PaymentRollbackEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public class PaymentRollbackEventKafkaConsumerIT extends AbstractIT {

    @Autowired
    PaymentRollbackEventKafkaStreamTestPublisher paymentRollbackEventKafkaStreamTestPublisher;

    @Autowired
    FakePaymentRollbackCommandHandler paymentRollbackCommandHandler;

    EventAssertion<PaymentRollbackEvent, PaymentRollback> eventAssertion = new EventAssertion<>();

    @Test
    void should_receive_payment_rollback_events() {
        //given
        PaymentRollbackEvent paymentRollbackEvent = PaymentRollbackEvent.builder()
                .id(1L)
                .eventCreatedAt(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                .build();

        //when
        paymentRollbackEventKafkaStreamTestPublisher.publish(paymentRollbackEvent);

        //then
        eventAssertion.assertEventProcessed(
                5,
                paymentRollbackEvent,
                () -> paymentRollbackCommandHandler.getProcessedPaymentRollback());
    }

}
