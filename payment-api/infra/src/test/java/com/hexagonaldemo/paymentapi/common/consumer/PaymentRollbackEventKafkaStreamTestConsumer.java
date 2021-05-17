package com.hexagonaldemo.paymentapi.common.consumer;

import com.hexagonaldemo.paymentapi.common.config.AbstractEventKafkaStreamTestConsumer;
import com.hexagonaldemo.paymentapi.common.config.KafkaTestStreams;
import com.hexagonaldemo.paymentapi.payment.event.PaymentRollbackEvent;
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
