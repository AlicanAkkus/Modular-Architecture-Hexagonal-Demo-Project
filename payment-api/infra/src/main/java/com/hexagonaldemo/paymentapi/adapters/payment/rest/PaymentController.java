package com.hexagonaldemo.paymentapi.adapters.payment.rest;

import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import com.hexagonaldemo.paymentapi.payment.PaymentFacade;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController extends BaseController {

    private final PaymentFacade paymentFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PaymentResponse> pay(@RequestBody @Valid PaymentCreateRequest paymentCreateRequest) {
        Payment payment = paymentFacade.pay(paymentCreateRequest.toModel());
        return respond(PaymentResponse.fromModel(payment));
    }
}