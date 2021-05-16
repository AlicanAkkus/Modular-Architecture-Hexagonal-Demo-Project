package com.hexagonaldemo.paymentapi.payment.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@With
@EqualsAndHashCode
public class Payment {

    private Long id;
    private LocalDateTime createdAt;
    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
    private PaymentState state;
}