package com.hexagonaldemo.ticketapi.ticket;

import com.hexagonaldemo.ticketapi.account.model.Account;
import com.hexagonaldemo.ticketapi.account.port.AccountDataPort;
import com.hexagonaldemo.ticketapi.event.model.Event;
import com.hexagonaldemo.ticketapi.event.port.EventDataPort;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import com.hexagonaldemo.ticketapi.ticket.command.BuyTicket;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import com.hexagonaldemo.ticketapi.ticket.port.TicketPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketFacade {

    private final EventDataPort eventDataPort;
    private final TicketPort ticketPort;
    private final PaymentPort paymentPort;
    private final AccountDataPort accountDataPort;

    public Ticket buy(BuyTicket buyTicket) {
        Account account = accountDataPort.retrieve(buyTicket.getAccountId());
        Event event = eventDataPort.retrieve(buyTicket.getEventId());

        Payment payment = paymentPort.pay(buildCreatePayment(account.getId(), event.getPrice(), buyTicket.getCount(), buyTicket.getReferenceCode()));
        log.debug("Ticket price payed by account {} as {}", account.getId(), payment.getPrice());

        return ticketPort.buy(buyTicket);
    }

    private CreatePayment buildCreatePayment(Long accountId, BigDecimal eventPrice, Integer ticketCount, String referenceCode) {
        return CreatePayment.builder()
                .accountId(accountId)
                .referenceCode(referenceCode)
                .price(eventPrice.multiply(BigDecimal.valueOf(ticketCount)))
                .build();
    }
}
