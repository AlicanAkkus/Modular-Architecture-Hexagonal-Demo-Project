package com.hexagonaldemo.paymentapi.adapters.balance.rest;

import com.hexagonaldemo.paymentapi.common.commandhandler.VoidEmptyCommandHandler;
import com.hexagonaldemo.paymentapi.common.rest.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(name = "administration.enabled", havingValue = "true")
@RequestMapping("/api/v1/balances")
public class BalanceAdminController extends BaseController {

    @Qualifier("balanceAdminCommandHandler")
    private final VoidEmptyCommandHandler balanceAdminCommandHandler;

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteBalances() {
        balanceAdminCommandHandler.handle();
        log.info("All balances are deleted");
    }
}