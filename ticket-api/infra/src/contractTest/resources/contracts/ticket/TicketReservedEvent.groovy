package contracts.ticket

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label "ticket reserved event"
    description "should output event to ticketReserved.test.topic channel"
    input {
        triggeredBy("emitTicketReservedEvent()")
    }
    outputMessage {
        sentTo("ticketReserved.test.topic")
        body(
                """
                    {
                      "id": 300,
                      "accountId": 232,
                      "meetupId": 342,
                      "reserveDate": "2021-05-29T15:15:00",
                      "price": 60,
                      "count": 2,
                      "paymentId": 3221,
                      "eventCreatedAt": "2021-05-29T15:15:02"
                    }
            """)
    }
}