package com.hexagonaldemo.ticketapi.common.commandhandler;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.meetup.MeetupCreateCommandHandler;
import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.ticket.command.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeMeetupCreateCommandHandler implements CommandHandler<Meetup, MeetupCreate> {

    private static final String CANNOT_CREATE_NAME = "FAIL";
    private static final List<String> FAILING_NAMES = List.of(
            CANNOT_CREATE_NAME
    );

    @Override
    public Meetup handle(MeetupCreate meetupCreate) {
         if (meetupCreate.getName().equals(CANNOT_CREATE_NAME)) {
            throw new TicketApiBusinessException("ticketapi.meetup.cannotCreate");
        }
        if (!FAILING_NAMES.contains(meetupCreate.getName())) {
            return Meetup.builder()
                    .id(1L)
                    .name(meetupCreate.getName())
                    .website(meetupCreate.getWebsite())
                    .price(meetupCreate.getPrice())
                    .eventDate(meetupCreate.getEventDate())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
