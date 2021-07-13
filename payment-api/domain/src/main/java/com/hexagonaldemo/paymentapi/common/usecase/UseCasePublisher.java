package com.hexagonaldemo.paymentapi.common.usecase;

import com.hexagonaldemo.paymentapi.common.model.UseCase;

public interface UseCasePublisher {

    <R, T extends UseCase> R publish(Class<R> returnClass, T useCase);

    <R, T extends UseCase> void publish(T useCase);
}
