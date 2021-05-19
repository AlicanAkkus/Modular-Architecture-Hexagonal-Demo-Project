package com.hexagonaldemo.suite.behavior

import groovy.util.logging.Slf4j

@Slf4j
trait TicketBehaviors {

    def reserveTicket(accountId, meetupId, count, referenceCode) {
        def response = httpClient.call("ticket_reserve", "POST", [
                path: "/api/v1/tickets",
                body: [
                        "accountId"    : accountId,
                        "meetupId"     : meetupId,
                        "count"        : count,
                        "referenceCode": referenceCode
                ]
        ])
        return response
    }

    def retrieveTicket(accountId) {
        def response = httpClient.call("ticket_retrieve", "GET", [
                path: "/api/v1/tickets",
                body: [
                        "accountId": accountId
                ]
        ])
        return response
    }
}
