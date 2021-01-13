package ticket

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "should buy ticket"
    description "should buy ticket for event"
    request {
        url "/api/v1/tickets"
        method("POST")
        headers {
            header(contentType(), applicationJson())
        }
        body(
                """
                    {
                        "accountId": 123,
                        "eventId": 5,
                        "count": 2,
                        "referenceCode": "123e4567-e89b-12d3-a456-426614174000"
                    }
                """
        )
    }
    response {
        status(CREATED())
        body(
                """
                    {
                        "data": {
                            "id": 1,
                            "accountId": 123,
                            "eventId": 5,
                            "count": 2,
                            "boughtDate": "2020-01-01T12:12:12",
                            "price": 90.00
                        }
                    }
                """
        )
    }
}