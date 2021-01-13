package com.hexagonaldemo.ticketapi.event.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Event {

    private Long id;
    private String name;
    private String website;
    private BigDecimal price;
    private LocalDateTime eventDate;
}
