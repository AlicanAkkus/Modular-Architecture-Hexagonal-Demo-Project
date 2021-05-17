package com.hexagonaldemo.paymentapi.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexagonaldemo.paymentapi.common.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public abstract class AbstractTestPublisher<T extends Event<?>> {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected KafkaEventTestStreams kafkaEventTestStreams;

    public abstract MessageChannel getOutputChannel();

    public void publish(T event) {
        try {
            String serializedEvent = objectMapper.writeValueAsString(event);
            getOutputChannel().send(MessageBuilder.withPayload(serializedEvent).build());
            log.info("Event is published: Event {}", event);
        } catch (Exception e) {
            log.info("Event cannot be published due to error: Event {}", event, e);
        }
    }
}
