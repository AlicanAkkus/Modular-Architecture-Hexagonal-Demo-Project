package com.hexagonaldemo.paymentapi.payment.model;

public enum PaymentState {
    SUCCESS,
    PENDING,
    ROLLBACKED;

    public boolean isRollbackable() {
        return PENDING.equals(this);
    }
}
