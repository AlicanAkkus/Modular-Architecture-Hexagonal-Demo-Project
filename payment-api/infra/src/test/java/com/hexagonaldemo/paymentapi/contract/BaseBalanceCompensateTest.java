package com.hexagonaldemo.paymentapi.contract;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

public class BaseBalanceCompensateTest extends AbstractContractTest {

    @Override
    void setUp() {
        doNothing().when(balanceCompensateUseCaseHandler).handle(any());
    }
}