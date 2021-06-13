package com.hexagonaldemo.ticketapi.adapters;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.command.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TicketFakeDataAdapter implements TicketPort {

    private static final Long CREATE_FAIL_ACCOUNT_ID = 6662L;
    private static final Long NO_TICKET_ACCOUNT_ID = 6663L;
    private static final List<Long> FAILING_IDS = List.of(CREATE_FAIL_ACCOUNT_ID);

    @Override
    public Ticket create(CreateTicket createTicket) {
        if (createTicket.getAccountId().equals(CREATE_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.ticket.cannotBeCreated");
        }
        if (!FAILING_IDS.contains(createTicket.getAccountId())) {
            return Ticket.builder()
                    .id(1L)
                    .accountId(createTicket.getAccountId())
                    .count(createTicket.getCount())
                    .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                    .price(BigDecimal.valueOf(100.00))
                    .meetupId(createTicket.getMeetupId())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }

    @Override
    public List<Ticket> retrieve(TicketRetrieve ticketRetrieve) {
        if (ticketRetrieve.getAccountId().equals(NO_TICKET_ACCOUNT_ID)) return List.of();
        return List.of(Ticket.builder()
                        .id(2L)
                        .count(1)
                        .meetupId(1001L)
                        .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                        .price(BigDecimal.valueOf(100.00))
                        .accountId(ticketRetrieve.getAccountId())
                        .build(),
                Ticket.builder()
                        .id(2L)
                        .count(1)
                        .meetupId(1002L)
                        .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                        .price(BigDecimal.valueOf(110.00))
                        .accountId(ticketRetrieve.getAccountId())
                        .build(),
                Ticket.builder()
                        .id(3L)
                        .count(1)
                        .meetupId(1003L)
                        .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                        .price(BigDecimal.valueOf(120.00))
                        .accountId(ticketRetrieve.getAccountId())
                        .build());
    }

    @Override
    public void deleteAll() {

    }
}
