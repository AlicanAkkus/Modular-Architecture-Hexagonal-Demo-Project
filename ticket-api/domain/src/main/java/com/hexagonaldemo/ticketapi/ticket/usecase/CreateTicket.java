package com.hexagonaldemo.ticketapi.ticket.usecase;

import com.hexagonaldemo.ticketapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTicket implements UseCase {

    private Long accountId;
    private Long meetupId;
    private Integer count;
    private String referenceCode;
}
