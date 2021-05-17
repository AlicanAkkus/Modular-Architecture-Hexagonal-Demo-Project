package com.hexagonaldemo.ticketapi.reservation.command;

import com.hexagonaldemo.ticketapi.common.model.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketReserve implements Command {

    private Long accountId;
    private Long meetupId;
    private Integer count;
    private String referenceCode;
}
