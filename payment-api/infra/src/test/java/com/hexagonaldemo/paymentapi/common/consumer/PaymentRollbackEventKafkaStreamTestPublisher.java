package com.hexagonaldemo.paymentapi.common.consumer;

import com.hexagonaldemo.paymentapi.common.config.AbstractTestPublisher;
import com.hexagonaldemo.paymentapi.common.config.KafkaEventTestStreams;
import com.hexagonaldemo.paymentapi.payment.event.PaymentRollbackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentRollbackEventKafkaStreamTestPublisher extends AbstractTestPublisher<PaymentRollbackEvent> {

    public final KafkaEventTestStreams kafkaEventTestStreams;

    @Override
    public MessageChannel getOutputChannel() {
        return kafkaEventTestStreams.paymentRollbackOutput();
    }
}
