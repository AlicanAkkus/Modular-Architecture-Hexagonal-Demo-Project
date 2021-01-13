package com.hexagonaldemo.ticketapi.payment.command;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreatePayment {

    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
}
