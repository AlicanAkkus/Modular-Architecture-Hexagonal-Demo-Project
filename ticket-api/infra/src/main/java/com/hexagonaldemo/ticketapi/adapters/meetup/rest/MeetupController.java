package com.hexagonaldemo.ticketapi.adapters.meetup.rest;

import com.hexagonaldemo.ticketapi.adapters.meetup.rest.dto.MeetupCreateRequest;
import com.hexagonaldemo.ticketapi.adapters.meetup.rest.dto.MeetupResponse;
import com.hexagonaldemo.ticketapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.meetup.usecase.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetups")
public class MeetupController extends BaseController {

    private final UseCaseHandler<Meetup, MeetupCreate> meetupCreateUseCaseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<MeetupResponse> createMeetup(@Valid @RequestBody MeetupCreateRequest meetupCreateRequest) {
        var meetup = meetupCreateUseCaseHandler.handle(meetupCreateRequest.toUseCase());
        return respond(MeetupResponse.from(meetup));
    }
}
