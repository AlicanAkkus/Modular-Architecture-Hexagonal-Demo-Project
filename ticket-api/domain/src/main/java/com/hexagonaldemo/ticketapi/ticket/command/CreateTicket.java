package com.hexagonaldemo.ticketapi.ticket.command;

import com.hexagonaldemo.ticketapi.common.model.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTicket implements Command {

    private Long accountId;
    private Long meetupId;
    private Integer count;
    private String referenceCode;
}
