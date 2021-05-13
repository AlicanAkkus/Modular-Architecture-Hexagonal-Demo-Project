package com.hexagonaldemo.ticketapi.adapters.reservation.event.message;

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
public class TicketReservedEvent implements Event {

    private LocalDateTime eventCreatedAt;
    private Long id;
    private Long accountId;
    private Long meetupId;
    private LocalDateTime reserveDate;
    private BigDecimal price;
    private Integer count;

    public static TicketReservedEvent from(Ticket ticket) {
        return TicketReservedEvent.builder()
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

