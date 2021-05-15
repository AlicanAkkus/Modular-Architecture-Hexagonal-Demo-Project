package com.hexagonaldemo.ticketapi.common.commandhandler;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.reservation.command.ReserveTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeReserveTicketCommandHandler implements CommandHandler<Ticket, ReserveTicket> {

    private static final Long PAYMENT_FAIL_ACCOUNT_ID = 6661L;
    private static final Long TICKET_CREATE_FAIL_ACCOUNT_ID = 6662L;
    private static final List<Long> FAILING_IDS = List.of(
            PAYMENT_FAIL_ACCOUNT_ID,
            TICKET_CREATE_FAIL_ACCOUNT_ID
    );

    @Override
    public Ticket handle(ReserveTicket reserveTicket) {
        failedPaymentScenario(reserveTicket);
        failedCreateTicketScenario(reserveTicket);
        return succeededReserveTicketScenario(reserveTicket);
    }

    private void failedCreateTicketScenario(ReserveTicket reserveTicket) {
        if (reserveTicket.getAccountId().equals(TICKET_CREATE_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.ticket.cannotBeCreated");
        }
    }

    private void failedPaymentScenario(ReserveTicket reserveTicket) {
        if (reserveTicket.getAccountId().equals(PAYMENT_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.payment.client.error");
        }
    }

    private Ticket succeededReserveTicketScenario(ReserveTicket reserveTicket) {
        if (!FAILING_IDS.contains(reserveTicket.getAccountId())) {
            return Ticket.builder()
                    .id(1L)
                    .count(reserveTicket.getCount())
                    .meetupId(reserveTicket.getMeetupId())
                    .reserveDate(LocalDateTime.of(2021,1,1,19,0,0))
                    .price(BigDecimal.valueOf(100.00))
                    .accountId(reserveTicket.getAccountId())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
