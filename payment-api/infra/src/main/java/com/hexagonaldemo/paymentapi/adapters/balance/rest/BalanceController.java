package com.hexagonaldemo.paymentapi.adapters.balance.rest;

import com.hexagonaldemo.paymentapi.adapters.balance.rest.dto.BalanceResponse;
import com.hexagonaldemo.paymentapi.balance.BalanceFacade;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balances")
public class BalanceController extends BaseController {

    private final BalanceFacade balanceFacade;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<BalanceResponse> retrieve(@RequestParam("accountId") Long accountId) {
        Balance balance = balanceFacade.retrieve(accountId);
        log.info("Balance is retrieved for account {} as {}", accountId, balance);
        return respond(BalanceResponse.fromModel(balance));
    }
}