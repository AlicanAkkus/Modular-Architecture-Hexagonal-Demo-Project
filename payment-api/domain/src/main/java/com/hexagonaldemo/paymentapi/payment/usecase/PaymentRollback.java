package com.hexagonaldemo.paymentapi.payment.usecase;

import com.hexagonaldemo.paymentapi.common.model.UseCase;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PaymentRollback implements UseCase {

    private Long id;
    private BigDecimal price;
    private Long accountId;
}

