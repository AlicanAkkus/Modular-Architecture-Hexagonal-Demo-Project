package com.hexagonaldemo.ticketapi.common.usecase;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.reservation.usecase.TicketReserve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "false", matchIfMissing = true)
public class FakeTicketReserveUseCaseHandler implements UseCaseHandler<Ticket, TicketReserve> {

    private static final Long PAYMENT_FAIL_ACCOUNT_ID = 6661L;
    private static final Long TICKET_CREATE_FAIL_ACCOUNT_ID = 6662L;
    private static final List<Long> FAILING_IDS = List.of(
            PAYMENT_FAIL_ACCOUNT_ID,
            TICKET_CREATE_FAIL_ACCOUNT_ID
    );

    @Override
    public Ticket handle(TicketReserve useCase) {
        failedPaymentScenario(useCase);
        failedCreateTicketScenario(useCase);
        return succeededReserveTicketScenario(useCase);
    }

    private void failedCreateTicketScenario(TicketReserve ticketReserve) {
        if (ticketReserve.getAccountId().equals(TICKET_CREATE_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.ticket.cannotBeCreated");
        }
    }

    private void failedPaymentScenario(TicketReserve ticketReserve) {
        if (ticketReserve.getAccountId().equals(PAYMENT_FAIL_ACCOUNT_ID)) {
            throw new TicketApiBusinessException("ticketapi.payment.client.error");
        }
    }

    private Ticket succeededReserveTicketScenario(TicketReserve ticketReserve) {
        if (!FAILING_IDS.contains(ticketReserve.getAccountId())) {
            return Ticket.builder()
                    .id(1L)
                    .count(ticketReserve.getCount())
                    .meetupId(ticketReserve.getMeetupId())
                    .reserveDate(LocalDateTime.of(2021,1,1,19,0,0))
                    .price(BigDecimal.valueOf(100.00))
                    .accountId(ticketReserve.getAccountId())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
