package com.hexagonaldemo.ticketapi.adapters.meetup.jpa;

import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.entity.MeetupEntity;
import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.repository.MeetupJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.common.model.Status;
import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetupDataAdapter implements MeetupPort {

    private final MeetupJpaRepository meetupJpaRepository;

    @Override
    public Meetup retrieve(Long id) {
        return meetupJpaRepository.findById(id)
                .map(MeetupEntity::toModel)
                .orElseThrow(() -> new TicketApiDataNotFoundException("ticketapi.meetup.notFound"));
    }

    @Override
    public Meetup create(MeetupCreate meetupCreate) {
        var meetupEntity = new MeetupEntity();
        meetupEntity.setName(meetupCreate.getName());
        meetupEntity.setWebsite(meetupCreate.getWebsite());
        meetupEntity.setEventDate(meetupCreate.getEventDate());
        meetupEntity.setPrice(meetupCreate.getPrice());
        meetupEntity.setStatus(Status.ACTIVE);
        return meetupJpaRepository.save(meetupEntity).toModel();
    }

    @Override
    public void deleteAll() {
        meetupJpaRepository.deleteAll();
    }
}
