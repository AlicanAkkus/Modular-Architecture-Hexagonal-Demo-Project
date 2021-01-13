package com.hexagonaldemo.ticketapi.adapters.ticket.jpa.entity;

import com.hexagonaldemo.ticketapi.common.entity.AbstractEntity;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
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
@Table(name = "ticket")
@Where(clause = "status <> -1")
public class TicketEntity extends AbstractEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private LocalDateTime boughtDate;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long eventId;

    public Ticket toModel() {
        return Ticket.builder()
                .id(super.getId())
                .accountId(accountId)
                .eventId(eventId)
                .boughtDate(boughtDate)
                .price(price)
                .count(count)
                .build();
    }
}