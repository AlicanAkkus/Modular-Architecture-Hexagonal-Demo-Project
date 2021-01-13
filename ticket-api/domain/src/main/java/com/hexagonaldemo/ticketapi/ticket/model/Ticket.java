package com.hexagonaldemo.ticketapi.ticket.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Ticket {

    private Long id;
    private Long accountId;
    private Long eventId;
    private LocalDateTime boughtDate;
    private BigDecimal price;
    private Integer count;
}
