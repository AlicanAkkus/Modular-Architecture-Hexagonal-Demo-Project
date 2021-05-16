package com.hexagonaldemo.ticketapi.payment.command;

import com.hexagonaldemo.ticketapi.common.model.Command;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentCreate implements Command {

    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
}
