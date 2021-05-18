package com.hexagonaldemo.ticketapi.ticket.command;

import com.hexagonaldemo.ticketapi.common.model.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRetrieve implements Command {

    private Long accountId;
}
