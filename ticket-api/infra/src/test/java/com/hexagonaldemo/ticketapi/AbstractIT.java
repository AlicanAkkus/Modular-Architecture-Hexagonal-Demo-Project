package com.hexagonaldemo.ticketapi;

import com.hexagonaldemo.ticketapi.kafka.TicketEventKafkaStreamConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

public abstract class AbstractIT {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected TicketEventKafkaStreamConsumer ticketEventKafkaStreamConsumer;

    @LocalServerPort
    protected Integer port;

    @BeforeEach
    void setUp() {
        ticketEventKafkaStreamConsumer.reset();
    }
}