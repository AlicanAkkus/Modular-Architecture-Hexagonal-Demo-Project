package com.hexagonaldemo.suite.behavior

import groovy.util.logging.Slf4j

@Slf4j
trait TicketBehaviors {

    def reserveTicket(accountId, meetupId, count) {
        def response = httpClient.call("ticket_reserve", "POST", [
                path: "/api/v1/tickets",
                body: [
                        "accountId"    : accountId,
                        "meetupId"     : meetupId,
                        "count"        : count,
                        "referenceCode": "ref code"
                ]
        ])
        return response
    }

    def retrieveTickets(accountId) {
        def response = httpClient.call("ticket_retrieve", "GET", [
                path: "/api/v1/tickets",
                query: [
                        "accountId": accountId
                ]
        ])
        return response.data.items
    }

    def createMeetup(name, price, eventDate) {
        def response = httpClient.call("meetup_create", "POST", [
                path: "/api/v1/meetups",
                body: [
                        "name"     : name,
                        "website"  : "http:://conference.com",
                        "price"    : price,
                        "eventDate": "${eventDate}T10:00:00"
                ]
        ])
        return response.data
    }

    def deleteAllMeetups() {
        def response = httpClient.call("meetup_deleteAll", "DELETE", [
                path: "/api/v1/meetups"
        ])
    }

    def deleteAllTickets() {
        def response = httpClient.call("ticket_deleteAll", "DELETE", [
                path: "/api/v1/tickets"
        ])
    }
}
