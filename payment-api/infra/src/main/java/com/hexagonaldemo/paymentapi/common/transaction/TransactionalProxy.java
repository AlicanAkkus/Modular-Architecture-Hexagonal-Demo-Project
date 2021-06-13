package com.hexagonaldemo.paymentapi.common.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Component
public class TransactionalProxy {

    @Transactional
    public <I> void executeForVoid(I input, FunctionVoid<I> func) {
        func.apply(input);
    }

    @Transactional
    public <I, O> O executeForValue(I input, Function<I, O> func) {
        return func.apply(input);
    }
}
