package com.hexagonaldemo.ticketapi.ticket.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyTicket {

    private Long accountId;
    private Long eventId;
    private Integer count;
    private String referenceCode;
}
