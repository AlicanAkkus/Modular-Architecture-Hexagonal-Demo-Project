package com.hexagonaldemo.ticketapi.reservation.usecase;

import com.hexagonaldemo.ticketapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketReserve implements UseCase {

    private Long accountId;
    private Long meetupId;
    private Integer count;
    private String referenceCode;
}
