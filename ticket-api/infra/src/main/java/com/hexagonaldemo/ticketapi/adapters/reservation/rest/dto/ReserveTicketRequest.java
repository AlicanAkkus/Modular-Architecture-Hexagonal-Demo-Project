package com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto;

import com.hexagonaldemo.ticketapi.reservation.usecase.TicketReserve;
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
public class ReserveTicketRequest {

    @NotNull
    @Positive
    private Long accountId;

    @NotNull
    @Positive
    private Long meetupId;

    @NotNull
    @Positive
    private Integer count;

    private String referenceCode;

    public TicketReserve toModel() {
        return TicketReserve.builder()
                .accountId(accountId)
                .meetupId(meetupId)
                .count(count)
                .referenceCode(calculateReferenceCode())
                .build();
    }

    private String calculateReferenceCode() {
        return Optional.ofNullable(referenceCode)
                .orElse(UUID.randomUUID().toString());
    }
}
