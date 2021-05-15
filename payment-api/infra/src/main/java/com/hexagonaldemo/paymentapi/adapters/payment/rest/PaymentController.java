package com.hexagonaldemo.paymentapi.adapters.payment.rest;

import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.paymentapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import com.hexagonaldemo.paymentapi.payment.PaymentCreateCommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController extends BaseController {

    private final CommandHandler<Payment, PaymentCreate> paymentCreateCommandHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PaymentResponse> pay(@RequestBody @Valid PaymentCreateRequest paymentCreateRequest) {
        var payment = paymentCreateCommandHandler.handle(paymentCreateRequest.toModel());
        return respond(PaymentResponse.fromModel(payment));
    }
}