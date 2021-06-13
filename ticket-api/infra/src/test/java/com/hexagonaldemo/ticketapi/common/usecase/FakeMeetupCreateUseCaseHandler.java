package com.hexagonaldemo.ticketapi.common.usecase;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.meetup.usecase.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "false", matchIfMissing = true)
public class FakeMeetupCreateUseCaseHandler implements UseCaseHandler<Meetup, MeetupCreate> {

    private static final String CANNOT_CREATE_NAME = "FAIL";
    private static final List<String> FAILING_NAMES = List.of(
            CANNOT_CREATE_NAME
    );

    @Override
    public Meetup handle(MeetupCreate useCase) {
         if (useCase.getName().equals(CANNOT_CREATE_NAME)) {
            throw new TicketApiBusinessException("ticketapi.meetup.cannotCreate");
        }
        if (!FAILING_NAMES.contains(useCase.getName())) {
            return Meetup.builder()
                    .id(1L)
                    .name(useCase.getName())
                    .website(useCase.getWebsite())
                    .price(useCase.getPrice())
                    .eventDate(useCase.getEventDate())
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
