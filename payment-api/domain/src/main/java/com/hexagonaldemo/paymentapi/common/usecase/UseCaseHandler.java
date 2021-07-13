package com.hexagonaldemo.paymentapi.common.usecase;

import com.hexagonaldemo.paymentapi.common.model.UseCase;

public interface UseCaseHandler<R, T extends UseCase> {

    R handle(T useCase);
}
