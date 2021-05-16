package com.hexagonaldemo.ticketapi.common.event.consumer;

import com.hexagonaldemo.ticketapi.payment.event.PaymentRollbackEvent;
import com.hexagonaldemo.ticketapi.common.event.configuration.KafkaTestStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRollbackEventKafkaStreamTestConsumer extends AbstractEventKafkaStreamTestConsumer<PaymentRollbackEvent> {

    @Override
    @StreamListener(KafkaTestStreams.PAYMENT_ROLLBACK_INPUT)
    public void consume(@Payload PaymentRollbackEvent event) {
        consumerInternal(event);
    }
}
