package com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto;

import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Long id;
    private Long accountId;
    private Long eventId;
    private LocalDateTime boughtDate;
    private BigDecimal price;
    private Integer count;

    public static TicketResponse fromModel(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .accountId(ticket.getAccountId())
                .eventId(ticket.getEventId())
                .boughtDate(ticket.getBoughtDate())
                .price(ticket.getPrice())
                .count(ticket.getCount())
                .build();
    }
}
