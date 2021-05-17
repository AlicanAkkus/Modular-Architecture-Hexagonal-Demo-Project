package com.hexagonaldemo.paymentapi.common.config;

import com.hexagonaldemo.paymentapi.common.model.Event;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@Service
public abstract class AbstractEventKafkaStreamTestConsumer<T extends Event> {

    private Deque<T> eventsQueue;

    public abstract void consume(@Payload T event);

    protected void consumerInternal(T event) {
        log.info("Event received:: {}", event);

        initializeEventQueue();
        keepEvent(event);

        log.info("Event added to the kafka event list as {}th entry, event: {}", queueSize(), event);
    }

    @Synchronized
    public void wait(int maxWaitInSec, int expectedEventSize) {
        initializeEventQueue();
        try {
            LocalDateTime startTime = LocalDateTime.now();
            int checkCount = 0;
            while (true) {
                if (!(checkCount++ < maxWaitInSec)) break;
                if (eventsQueue.size() > expectedEventSize) break;
                sleepOneSecond();
            }
            LocalDateTime endTime = LocalDateTime.now();
            log.info("Tried {} seconds. Expected event count is {} and actual event size is {}", Duration.between(startTime, endTime).toSeconds(), expectedEventSize, eventsQueue.size());
        } catch (Exception e) {
            log.info("Expected event count is {} and actual event size is {}" + e, expectedEventSize, Objects.isNull(eventsQueue) ? 0 : eventsQueue.size());
        }
    }

    protected void initializeEventQueue() {
        if (Objects.isNull(eventsQueue)) {
            this.eventsQueue = new ConcurrentLinkedDeque<>();
        }
    }

    protected void keepEvent(T event) {
        eventsQueue.add(event);
    }

    protected int queueSize() {
        return eventsQueue.size();
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(1000); //NOSONAR
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }

    public void reset() {
        eventsQueue = null;
    }

    public T pop() {
        T event = eventsQueue.pollFirst();
        log.info("Last event from the consumed list is popped for validation. Event: {}", event);
        return event;
    }

    @Synchronized
    public List<T> popAll() {
        if (Objects.isNull(this.eventsQueue)) return List.of();
        List<T> eventsAsList = new ArrayList<>(this.eventsQueue);
        log.info("All {} events from the consumed list is popped for validation.", eventsQueue.size());
        eventsQueue.clear();
        return eventsAsList;
    }
}
