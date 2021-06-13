package com.hexagonaldemo.ticketapi.adapters.meetup.rest.dto;

import com.hexagonaldemo.ticketapi.meetup.usecase.MeetupCreate;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private String website;

    @NotNull
    private BigDecimal price;

    @NotNull
    private LocalDateTime eventDate;

    public MeetupCreate toUseCase() {
        return MeetupCreate.builder()
                .name(name)
                .website(website)
                .price(price)
                .eventDate(eventDate)
                .build();
    }
}
