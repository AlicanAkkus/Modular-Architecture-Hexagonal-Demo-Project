package com.hexagonaldemo.paymentapi.adapters.payment.rest;

import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import com.hexagonaldemo.paymentapi.common.transaction.TransactionalProxy;
import com.hexagonaldemo.paymentapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController extends BaseController {

    private final UseCaseHandler<Payment, PaymentCreate> paymentCreateUseCaseHandler;
    private final TransactionalProxy transactionalProxy;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PaymentResponse> pay(@RequestBody @Valid PaymentCreateRequest paymentCreateRequest) {
        var payment = transactionalProxy.executeForValue(paymentCreateRequest, useCase -> paymentCreateUseCaseHandler.handle(useCase.toModel()));
        return respond(PaymentResponse.fromModel(payment));
    }
}