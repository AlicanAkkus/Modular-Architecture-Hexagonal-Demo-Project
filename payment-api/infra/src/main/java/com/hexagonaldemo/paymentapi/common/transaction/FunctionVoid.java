package com.hexagonaldemo.paymentapi.common.transaction;

@FunctionalInterface
public interface FunctionVoid<T> {
     void apply(T t);
}
