package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentRollbackNotificationPort;
import com.hexagonaldemo.ticketapi.reservation.port.ReservationNotificationPort;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PaymentRollbackFakeDataAdapter implements PaymentRollbackNotificationPort {

    private List<Payment> events = new ArrayList<>();

    @Override
    public void publish(Payment payment) {
        events.add(payment);
        log.info("[FAKE] payment is published: {}", payment);
    }

    public void assertContains(Payment... payment) {
        assertThat(events).containsAnyElementsOf(List.of(payment));
    }

    public void assertNull() {
        assertThat(events).isEmpty();
    }

    public void reset() {
        events = new ArrayList<>();
    }
}
