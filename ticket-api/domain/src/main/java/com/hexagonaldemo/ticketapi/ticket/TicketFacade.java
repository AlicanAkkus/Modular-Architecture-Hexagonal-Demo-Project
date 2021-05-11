package com.hexagonaldemo.ticketapi.ticket;

import com.hexagonaldemo.ticketapi.account.port.AccountDataPort;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupDataPort;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentApiPort;
import com.hexagonaldemo.ticketapi.ticket.command.BuyTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketDataPort;
import com.hexagonaldemo.ticketapi.ticket.port.TicketEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketFacade {

    private final MeetupDataPort meetupDataPort;
    private final TicketDataPort ticketDataPort;
    private final PaymentApiPort paymentPort;
    private final AccountDataPort accountDataPort;
    private final TicketEventPort ticketEventPort;

    /**
     * Buy action contains a payment and a reservation.
     */
    public Ticket buy(BuyTicket buyTicket) {
        var account = accountDataPort.retrieve(buyTicket.getAccountId());
        var event = meetupDataPort.retrieve(buyTicket.getMeetupId());

        var payment = paymentPort.pay(buildCreatePayment(account.getId(), event.getPrice(), buyTicket.getCount(), buyTicket.getReferenceCode()));
        log.debug("Ticket price payed by account {} as {}", account.getId(), payment.getPrice());

        var reservedTicket = ticketDataPort.reserve(buyTicket);
        log.debug("Ticket price reserved by account {}", account.getId());

        ticketEventPort.publish(reservedTicket);
        log.debug("Ticket create event is sent for ticket {}", reservedTicket);

        return reservedTicket;
    }

    private CreatePayment buildCreatePayment(Long accountId, BigDecimal eventPrice, Integer ticketCount, String referenceCode) {
        return CreatePayment.builder()
                .accountId(accountId)
                .referenceCode(referenceCode)
                .price(eventPrice.multiply(BigDecimal.valueOf(ticketCount)))
                .build();
    }
}
