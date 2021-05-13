package com.hexagonaldemo.ticketapi;

import com.hexagonaldemo.ticketapi.common.event.consumer.ReservationEventKafkaStreamTestConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

public abstract class AbstractIT {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ReservationEventKafkaStreamTestConsumer reservationEventKafkaStreamTestConsumer;

    @LocalServerPort
    protected Integer port;

    @BeforeEach
    void setUp() {
        reservationEventKafkaStreamTestConsumer.reset();
    }
}