package com.hexagonaldemo.ticketapi.payment.port;

import com.hexagonaldemo.ticketapi.common.event.EventPublisher;
import com.hexagonaldemo.ticketapi.payment.event.PaymentRollbackEvent;
import org.springframework.stereotype.Component;

@Component
public interface PaymentRollbackEventPort extends EventPublisher<PaymentRollbackEvent> {

    void publish(PaymentRollbackEvent paymentRollbackEvent);
}
