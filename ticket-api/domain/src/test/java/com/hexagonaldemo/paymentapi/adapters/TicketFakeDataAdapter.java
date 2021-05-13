package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TicketFakeDataAdapter implements TicketPort {

    private static final Long CREATE_FAIL_ACCOUNT_ID = 6662L;
    private static final List<Long> FAILING_IDS = List.of(CREATE_FAIL_ACCOUNT_ID);

    @Override
    public Ticket create(CreateTicket createTicket) {
        failedCreateScenario(createTicket);
        return succeededCreateScenario(createTicket);
    }

    private void failedCreateScenario(CreateTicket reserveTicket) {
        if (reserveTicket.getAccountId().equals(CREATE_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.ticket.cannotBeCreated");
        }
    }

    private Ticket succeededCreateScenario(CreateTicket createTicket) {
        if (!FAILING_IDS.contains(createTicket.getAccountId())) {
            return Ticket.builder()
                    .id(1L)
                    .accountId(createTicket.getAccountId())
                    .count(createTicket.getCount())
                    .reserveDate(LocalDateTime.of(2021,1,1,19,0,0))
                    .price(BigDecimal.valueOf(100.00))
                    .meetupId(createTicket.getMeetupId())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
