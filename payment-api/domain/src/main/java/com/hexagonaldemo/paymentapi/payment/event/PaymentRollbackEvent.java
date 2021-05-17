package com.hexagonaldemo.paymentapi.payment.event;

import com.hexagonaldemo.paymentapi.common.model.Event;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRollbackEvent implements Event<PaymentRollback> {

    private LocalDateTime eventCreatedAt;
    private Long id;

    public PaymentRollback toModel() {
        return PaymentRollback.builder()
                .id(id)
                .build();
    }
}

