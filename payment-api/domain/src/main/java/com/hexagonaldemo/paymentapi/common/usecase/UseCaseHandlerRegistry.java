package com.hexagonaldemo.paymentapi.common.usecase;

import com.hexagonaldemo.paymentapi.common.model.UseCase;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class UseCaseHandlerRegistry {

    private Map<Class<? extends UseCase>, UseCaseHandler<?, ? extends UseCase>> registryForUseCaseHandlers;
    private Map<Class<? extends UseCase>, VoidUseCaseHandler<? extends UseCase>> registryForVoidUseCaseHandlers;
    private Map<Class<?>, NoUseCaseHandler<?>> registryForNoUseCaseHandlers;

    public static final UseCaseHandlerRegistry INSTANCE = new UseCaseHandlerRegistry();

    private UseCaseHandlerRegistry() {
        registryForUseCaseHandlers = new HashMap<>();
        registryForVoidUseCaseHandlers = new HashMap<>();
        registryForNoUseCaseHandlers = new HashMap<>();
    }

    public <R, T extends UseCase> void register(Class<T> key, UseCaseHandler<R, T> useCaseHandler) {
        log.info("Use case {} is registered by handler {}", key.getSimpleName(), useCaseHandler.getClass().getSimpleName());
        registryForUseCaseHandlers.put(key, useCaseHandler);
    }

    public <T extends UseCase> void register(Class<T> key, VoidUseCaseHandler<T> useCaseHandler) {
        log.info("Use case {} is registered by void handler {}", key.getSimpleName(), useCaseHandler.getClass().getSimpleName());
        registryForVoidUseCaseHandlers.put(key, useCaseHandler);
    }

    public <R> void register(Class<R> key, NoUseCaseHandler<R> useCaseHandler) {
        log.info("Use case {} is registered by no param handler {}", key.getSimpleName(), useCaseHandler.getClass().getSimpleName());
        registryForNoUseCaseHandlers.put(key, useCaseHandler);
    }

    public UseCaseHandler<?, ? extends UseCase> detectUseCaseHandlerFrom(Class<? extends UseCase> useCaseClass) {
        return registryForUseCaseHandlers.get(useCaseClass);
    }

    public VoidUseCaseHandler<? extends UseCase> detectVoidUseCaseHandlerFrom(Class<? extends UseCase> useCaseClass) {
        return registryForVoidUseCaseHandlers.get(useCaseClass);
    }

    public NoUseCaseHandler<?> detectNoUseCaseHandlerFrom(Class<?> returnClass) {
        return registryForNoUseCaseHandlers.get(returnClass);
    }
}
