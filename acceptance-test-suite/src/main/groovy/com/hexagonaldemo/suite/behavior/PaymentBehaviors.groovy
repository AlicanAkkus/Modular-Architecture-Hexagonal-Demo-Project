package com.hexagonaldemo.suite.behavior

import groovy.util.logging.Slf4j

@Slf4j
trait PaymentBehaviors {

    def pay(accountId, price, referenceCode) {
        def response = httpClient.call("payment_pay", "POST", [
                path: "/api/v1/payments",
                body: [
                        "accountId"    : accountId,
                        "price"        : price,
                        "referenceCode": referenceCode
                ]
        ])
        return response.data
    }

    def retrieveBalance(accountId) {
        def response = httpClient.call("balance_retrieve", "GET", [
                path : "/api/v1/balances",
                query: [
                        "accountId": accountId
                ]
        ])
        return response.data
    }

    def deposit(accountId, amount) {
        def response = httpClient.call("payment_pay", "POST", [
                path: "/api/v1/balances",
                body: [
                        "accountId": accountId,
                        "amount"   : amount,
                        "type"     : 'DEPOSIT'
                ]
        ])
        return response.data
    }

    def deleteAllBalances() {
        def response = httpClient.call("balance_deleteAll", "DELETE", [
                path: "/api/v1/balances"
        ])
    }
}
