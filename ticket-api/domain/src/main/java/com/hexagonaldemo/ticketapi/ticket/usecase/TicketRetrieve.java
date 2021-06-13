package com.hexagonaldemo.ticketapi.ticket.usecase;

import com.hexagonaldemo.ticketapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRetrieve implements UseCase {

    private Long accountId;
}
