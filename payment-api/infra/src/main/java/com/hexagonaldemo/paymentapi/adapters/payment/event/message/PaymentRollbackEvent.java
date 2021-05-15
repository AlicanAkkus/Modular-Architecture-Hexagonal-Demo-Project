package com.hexagonaldemo.paymentapi.adapters.payment.event.message;

import com.hexagonaldemo.paymentapi.common.model.Event;
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
}

