package com.hexagonaldemo.paymentapi.payment.model;

public enum PaymentState {
    SUCCESS,
    ROLLBACKED;

    public boolean isRollbackable() {
        return SUCCESS.equals(this);
    }
}
