package com.hexagonaldemo.paymentapi.common;

import com.hexagonaldemo.paymentapi.common.model.Event;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class EventAssertion<T extends Event<E>, E> {

    public void assertEventProcessed(int maxWaitInSec, T event, Supplier<E> resultAfterProcessing) {
        try {
            LocalDateTime startTime = LocalDateTime.now();
            int checkCount = 0;

            E processedModel;
            while (true) {
                processedModel = resultAfterProcessing.get();
                if (!(checkCount++ < maxWaitInSec)) break;
                if (Objects.nonNull(processedModel)) break;
                sleepOneSecond();
            }
            LocalDateTime endTime = LocalDateTime.now();
            log.info("Tried {} seconds. Expected event processed as {}", Duration.between(startTime, endTime).toSeconds(), processedModel);
            assertThat(processedModel).isEqualTo(event.toModel());
        } catch (Exception e) {
            log.info("Expected event not arrived in {} seconds", maxWaitInSec, e);
            fail("Expected event not arrived in " + maxWaitInSec + " seconds");
        }
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(1000); //NOSONAR
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }
}
