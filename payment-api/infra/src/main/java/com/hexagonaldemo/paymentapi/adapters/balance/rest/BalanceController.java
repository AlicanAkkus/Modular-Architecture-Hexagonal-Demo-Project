package com.hexagonaldemo.paymentapi.adapters.balance.rest;

import com.hexagonaldemo.paymentapi.adapters.balance.rest.dto.BalanceResponse;
import com.hexagonaldemo.paymentapi.adapters.balance.rest.dto.BalanceTransactionCreateRequest;
import com.hexagonaldemo.paymentapi.balance.command.BalanceRetrieve;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balances")
public class BalanceController extends BaseController {

    private final CommandHandler<Balance, BalanceRetrieve> balanceRetrieveCommandHandler;
    private final CommandHandler<Balance, BalanceTransactionCreate> balanceTransactionCreateCommandHandler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<BalanceResponse> retrieve(@RequestParam("accountId") Long accountId) {
        var balance = balanceRetrieveCommandHandler.handle(BalanceRetrieve.from(accountId));
        log.info("Balance is retrieved for account {} as {}", accountId, balance);
        return respond(BalanceResponse.fromModel(balance));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<BalanceResponse> addBalanceTransaction(@Valid @RequestBody BalanceTransactionCreateRequest balanceTransactionCreateRequest) {
        var balance = balanceTransactionCreateCommandHandler.handle(balanceTransactionCreateRequest.toCommand());
        log.info("Balance transaction is created as {} and balance became {}", balanceTransactionCreateRequest, balance);
        return respond(BalanceResponse.fromModel(balance));
    }
}