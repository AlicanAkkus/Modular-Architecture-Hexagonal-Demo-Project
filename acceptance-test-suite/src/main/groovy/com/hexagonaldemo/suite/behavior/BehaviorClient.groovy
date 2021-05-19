package com.hexagonaldemo.suite.behavior

import com.hexagonaldemo.suite.client.HttpClient
import groovy.util.logging.Slf4j

@Slf4j
class BehaviorClient implements TicketBehaviors, PaymentBehaviors {

    HttpClient httpClient

    BehaviorClient(HttpClient httpClient) {
        this.httpClient = httpClient
    }
}
