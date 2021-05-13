package com.hexagonaldemo.ticketapi.reservation;

import com.hexagonaldemo.ticketapi.account.port.AccountPort;
import com.hexagonaldemo.ticketapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentRollbackNotificationPort;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import com.hexagonaldemo.ticketapi.reservation.command.ReserveTicket;
import com.hexagonaldemo.ticketapi.reservation.port.ReservationNotificationPort;
import com.hexagonaldemo.ticketapi.ticket.command.CreateTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "true")
public class ReserveTicketCommandHandler implements CommandHandler<Ticket, ReserveTicket> {

    private final MeetupPort meetupPort;
    private final TicketPort ticketPort;
    private final PaymentPort paymentPort;
    private final AccountPort accountPort;
    private final ReservationNotificationPort reservationNotificationPort;
    private final PaymentRollbackNotificationPort paymentRollbackNotificationPort;

    /**
     * Buy action contains a payment and a reservation.
     */
    public Ticket handle(ReserveTicket reserveTicket) {
        var account = accountPort.retrieve(reserveTicket.getAccountId());
        var event = meetupPort.retrieve(reserveTicket.getMeetupId());

        var payment = paymentPort.pay(buildCreatePayment(account.getId(), event.getPrice(), reserveTicket.getCount(), reserveTicket.getReferenceCode()));
        log.debug("Ticket price payed by account {} as {}", account.getId(), payment.getPrice());

        try {

            var createdTicket = ticketPort.create(buildCreateTicket(reserveTicket));
            log.debug("Ticket price reserved by account {}", account.getId());

            reservationNotificationPort.publish(createdTicket);
            log.debug("Ticket create event is sent for ticket {}", createdTicket);

            return createdTicket;

        } catch (Exception e) {

            log.warn("Ticket cannot be created due to errors, payment will rollback.", e);
            paymentRollbackNotificationPort.publish(payment);

            throw new TicketApiBusinessException("ticketapi.ticket.cannotBeCreated");
        }
    }

    private CreateTicket buildCreateTicket(ReserveTicket reserveTicket) {
        return CreateTicket.builder()
                .accountId(reserveTicket.getAccountId())
                .count(reserveTicket.getCount())
                .meetupId(reserveTicket.getMeetupId())
                .referenceCode(reserveTicket.getReferenceCode())
                .build();
    }

    private CreatePayment buildCreatePayment(Long accountId, BigDecimal eventPrice, Integer ticketCount, String referenceCode) {
        return CreatePayment.builder()
                .accountId(accountId)
                .referenceCode(referenceCode)
                .price(eventPrice.multiply(BigDecimal.valueOf(ticketCount)))
                .build();
    }
}
