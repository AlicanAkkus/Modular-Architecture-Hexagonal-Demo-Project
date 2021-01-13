package com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto;

import com.hexagonaldemo.ticketapi.ticket.command.BuyTicket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyTicketRequest {

    @NotNull
    @Positive
    private Long accountId;

    @NotNull
    @Positive
    private Long eventId;

    @NotNull
    @Positive
    private Integer count;

    private String referenceCode;

    public BuyTicket toModel() {
        return BuyTicket.builder()
                .accountId(accountId)
                .eventId(eventId)
                .count(count)
                .referenceCode(Optional.ofNullable(referenceCode).orElse(UUID.randomUUID().toString()))
                .build();
    }
}
