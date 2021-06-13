package com.hexagonaldemo.ticketapi.common.usecase;

import com.hexagonaldemo.ticketapi.common.model.UseCase;

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
