package com.hexagonaldemo.ticketapi.adapters.event.jpa.entity;

import com.hexagonaldemo.ticketapi.common.entity.AbstractEntity;
import com.hexagonaldemo.ticketapi.event.model.Event;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event")
@Where(clause = "status <> -1")
public class EventEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    public Event toModel() {
        return Event.builder()
                .id(super.getId())
                .name(name)
                .website(website)
                .eventDate(eventDate)
                .price(price)
                .build();
    }
}