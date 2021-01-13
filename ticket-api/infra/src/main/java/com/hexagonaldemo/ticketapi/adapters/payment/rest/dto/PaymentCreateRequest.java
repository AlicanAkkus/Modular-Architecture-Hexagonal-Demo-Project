package com.hexagonaldemo.ticketapi.adapters.payment.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentCreateRequest {

    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
}