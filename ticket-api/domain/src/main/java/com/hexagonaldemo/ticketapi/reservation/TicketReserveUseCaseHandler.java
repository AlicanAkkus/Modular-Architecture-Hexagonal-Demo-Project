package com.hexagonaldemo.ticketapi.reservation;

import com.hexagonaldemo.ticketapi.account.port.AccountPort;
import com.hexagonaldemo.ticketapi.common.DomainComponent;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import com.hexagonaldemo.ticketapi.payment.event.PaymentRollbackEvent;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import com.hexagonaldemo.ticketapi.payment.port.PaymentRollbackEventPort;
import com.hexagonaldemo.ticketapi.payment.usecase.PaymentCreate;
import com.hexagonaldemo.ticketapi.reservation.port.TicketReservedEventPort;
import com.hexagonaldemo.ticketapi.reservation.usecase.TicketReserve;
import com.hexagonaldemo.ticketapi.ticket.event.TicketReservedEvent;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import com.hexagonaldemo.ticketapi.ticket.usecase.CreateTicket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class TicketReserveUseCaseHandler implements UseCaseHandler<Ticket, TicketReserve> {

    private final MeetupPort meetupPort;
    private final TicketPort ticketPort;
    private final PaymentPort paymentPort;
    private final AccountPort accountPort;
    private final TicketReservedEventPort reservationNotificationPort;
    private final PaymentRollbackEventPort paymentRollbackNotificationPort;

    /**
     * Buy action contains a payment and a reservation.
     */
    public Ticket handle(TicketReserve useCase) {
        var account = accountPort.retrieve(useCase.getAccountId());
        var event = meetupPort.retrieve(useCase.getMeetupId());

        var payment = paymentPort.pay(buildCreatePayment(account.getId(), event.getPrice(), useCase.getCount(), useCase.getReferenceCode()));
        log.debug("Ticket price payed by account {} as {}", account.getId(), payment.getPrice());

        try {
            var createdTicket = ticketPort.create(buildCreateTicket(useCase));
            log.debug("Ticket price reserved by account {}", account.getId());

            reservationNotificationPort.publish(TicketReservedEvent.from(createdTicket, payment));
            log.debug("Ticket create event is sent for ticket {}", createdTicket);

            return createdTicket;
        } catch (Exception e) {
            log.error("Ticket cannot be created due to errors, payment will rollback.", e);
            paymentRollbackNotificationPort.publish(PaymentRollbackEvent.from(payment));

            throw new TicketApiBusinessException("ticketapi.ticket.cannotBeCreated");
        }
    }

    private CreateTicket buildCreateTicket(TicketReserve ticketReserve) {
        return CreateTicket.builder()
                .accountId(ticketReserve.getAccountId())
                .count(ticketReserve.getCount())
                .meetupId(ticketReserve.getMeetupId())
                .referenceCode(ticketReserve.getReferenceCode())
                .build();
    }

    private PaymentCreate buildCreatePayment(Long accountId, BigDecimal eventPrice, Integer ticketCount, String referenceCode) {
        return PaymentCreate.builder()
                .accountId(accountId)
                .referenceCode(referenceCode)
                .price(eventPrice.multiply(BigDecimal.valueOf(ticketCount)))
                .build();
    }
}
