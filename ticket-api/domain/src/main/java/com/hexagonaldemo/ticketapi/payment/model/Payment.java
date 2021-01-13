package com.hexagonaldemo.ticketapi.payment.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Payment {

    private Long id;
    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
}