package com.hexagonaldemo.ticketapi.adapters.payment.event.message;

import com.hexagonaldemo.ticketapi.common.model.Event;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRollbackEvent implements Event {

    private LocalDateTime eventCreatedAt;
    private Long id;

    public static PaymentRollbackEvent from(Payment payment) {
        return PaymentRollbackEvent.builder()
                .eventCreatedAt(LocalDateTime.now())
                .id(payment.getId())
                .build();
    }
}

