package com.hexagonaldemo.paymentapi.payment.command;

import com.hexagonaldemo.paymentapi.common.model.Command;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PaymentRollback implements Command {

    private Long id;
    private BigDecimal price;
    private Long accountId;
}

