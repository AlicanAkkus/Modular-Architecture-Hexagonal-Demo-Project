package com.hexagonaldemo.ticketapi.adapters.ticket.event.message;

import com.hexagonaldemo.ticketapi.common.model.Event;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateEvent implements Event {

    private LocalDateTime eventCreatedAt;
    private Long id;
    private Long accountId;
    private Long meetupId;
    private LocalDateTime reserveDate;
    private BigDecimal price;
    private Integer count;

    public static TicketCreateEvent from(Ticket ticket) {
        return TicketCreateEvent.builder()
                .eventCreatedAt(LocalDateTime.now())
                .id(ticket.getId())
                .accountId(ticket.getAccountId())
                .meetupId(ticket.getMeetupId())
                .reserveDate(ticket.getReserveDate())
                .price(ticket.getPrice())
                .count(ticket.getCount())
                .build();
    }
}

