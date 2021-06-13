package com.hexagonaldemo.ticketapi.meetup;

import com.hexagonaldemo.ticketapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.ticketapi.meetup.usecase.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "usecase.enabled", havingValue = "true")
public class MeetupCreateUseCaseHandler implements UseCaseHandler<Meetup, MeetupCreate> {

    private final MeetupPort meetupPort;

    public Meetup handle(MeetupCreate useCase) {
        return meetupPort.create(useCase);
    }
}
