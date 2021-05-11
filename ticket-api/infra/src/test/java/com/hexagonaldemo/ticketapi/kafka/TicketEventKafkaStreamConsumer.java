package com.hexagonaldemo.ticketapi.kafka;

import com.hexagonaldemo.ticketapi.adapters.ticket.event.message.TicketCreateEvent;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
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
public class TicketEventKafkaStreamConsumer {

    private Deque<TicketCreateEvent> eventsQueue;

    @StreamListener(TicketEventKafkaTestStream.TICKET_CREATE_INPUT)
    public synchronized void consumeTicketCreate(@Payload TicketCreateEvent event) {
        log.info("Event received: {}", event);

        initializeEventQueue();
        eventsQueue.add(event);

        log.info("Event added to the kafka event list as {}th entry, event: {}", eventsQueue.size(), event);
    }

    public void wait(int sec, int expectedEventSize) {
        initializeEventQueue();
        try {
            LocalDateTime startTime = LocalDateTime.now();
            int checkCount = 0;
            while (true) {
                if (!(checkCount++ < sec)) break;
                if (eventsQueue.size() > expectedEventSize) break;
                sleepOneSecond();
            }
            LocalDateTime endTime = LocalDateTime.now();
            log.info("Tried {} seconds. Expected event count is {} and actual event size is {}", Duration.between(startTime, endTime).toSeconds(), expectedEventSize, eventsQueue.size());
        } catch (Exception e) {
            log.info("Expected event count is {} and actual event size is {}" + e, expectedEventSize, Objects.isNull(eventsQueue) ? 0 : eventsQueue.size());
        }
    }

    private void initializeEventQueue() {
        if (Objects.isNull(eventsQueue)) {
            this.eventsQueue = new ConcurrentLinkedDeque<>();
        }
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }

    public void reset() {
        eventsQueue = null;
    }

    public TicketCreateEvent pop() {
        TicketCreateEvent event = eventsQueue.pollFirst();
        log.info("Last event from the consumed list is popped for validation. Event: {}", event);
        return event;
    }

    @Synchronized
    public List<TicketCreateEvent> popAll() {
        if (Objects.isNull(this.eventsQueue)) return List.of();
        List<TicketCreateEvent> eventsAsList = new ArrayList<>(this.eventsQueue);
        log.info("All {} events from the consumed list is popped for validation.", eventsQueue.size());
        eventsQueue.clear();
        return eventsAsList;
    }
}
