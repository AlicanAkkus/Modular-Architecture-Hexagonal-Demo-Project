package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.payment.event.PaymentRollbackEvent;
import com.hexagonaldemo.ticketapi.payment.port.PaymentRollbackEventPort;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PaymentRollbackFakeDataAdapter implements PaymentRollbackEventPort {

    private List<PaymentRollbackEvent> events = new ArrayList<>();

    @Override
    public void publish(PaymentRollbackEvent paymentRollbackEvent) {
        events.add(paymentRollbackEvent);
        log.info("[FAKE] paymentRollbackEvent is published: {}", paymentRollbackEvent);
    }

    public void assertContains(PaymentRollbackEvent... paymentRollbackEvents) {
        assertThat(events).containsAnyElementsOf(List.of(paymentRollbackEvents));
    }

    public void assertNull() {
        assertThat(events).isEmpty();
    }

    public void reset() {
        events = new ArrayList<>();
    }
}
