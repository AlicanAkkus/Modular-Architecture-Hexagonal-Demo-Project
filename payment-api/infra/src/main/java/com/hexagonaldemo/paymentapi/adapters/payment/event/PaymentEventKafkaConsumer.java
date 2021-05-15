package com.hexagonaldemo.paymentapi.adapters.payment.event;

import com.hexagonaldemo.paymentapi.adapters.payment.event.message.PaymentRollbackEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentEventKafkaConsumer {

    @StreamListener(PaymentEventKafkaStream.PAYMENT_ROLLBACK_INPUT)
    public void consume(@Payload PaymentRollbackEvent event) {
        log.info("Payment roll back event received: {}", event);
    }
}
