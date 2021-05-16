package com.hexagonaldemo.ticketapi.ticket.event;

import com.hexagonaldemo.ticketapi.common.model.Event;
import com.hexagonaldemo.ticketapi.common.util.CurrentTimeFactory;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TicketReservedEvent implements Event {

    private LocalDateTime eventCreatedAt;
    private Long id;
    private Long accountId;
    private Long meetupId;
    private LocalDateTime reserveDate;
    private BigDecimal price;
    private Integer count;
    private Long paymentId;

    public static TicketReservedEvent from(Ticket ticket, Payment payment) {
        return TicketReservedEvent.builder()
                .eventCreatedAt(CurrentTimeFactory.now())
                .id(ticket.getId())
                .accountId(ticket.getAccountId())
                .meetupId(ticket.getMeetupId())
                .reserveDate(ticket.getReserveDate())
                .price(ticket.getPrice())
                .count(ticket.getCount())
                .paymentId(payment.getId())
                .build();
    }
}

