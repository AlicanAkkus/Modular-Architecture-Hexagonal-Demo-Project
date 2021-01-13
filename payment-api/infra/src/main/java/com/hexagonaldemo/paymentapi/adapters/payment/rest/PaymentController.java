package com.hexagonaldemo.paymentapi.adapters.payment.rest;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.paymentapi.balance.BalanceFacade;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import com.hexagonaldemo.paymentapi.payment.PaymentFacade;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController extends BaseController {

    private final PaymentFacade paymentFacade;
    private final BalanceFacade balanceFacade;
    private final AccountFacade accountFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PaymentResponse> payFromBalance(@RequestBody @Valid PaymentCreateRequest paymentCreateRequest) {
        accountFacade.makeBusy(paymentCreateRequest.getAccountId());

        Payment payment = paymentFacade.pay(paymentCreateRequest.toModel());
        Balance updatedBalance = balanceFacade.retrieve(paymentCreateRequest.getAccountId());

        accountFacade.makeFree(paymentCreateRequest.getAccountId());
        log.info("Balance amount has {} after payment {} for account {}", updatedBalance.getAmount(), payment, paymentCreateRequest.getAccountId());
        return respond(PaymentResponse.fromModel(payment));
    }
}