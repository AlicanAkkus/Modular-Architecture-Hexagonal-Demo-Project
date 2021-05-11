package contracts.balance

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "retrieve balance by account id"
    description "should retrieve balance by id"

    request {
        url("/api/v1/balances") {
            queryParameters {
                parameter("accountId", 1)
            }
        }

        headers {
            header(contentType(), applicationJson())
        }
        method GET()
    }

    response {
        status OK()
        body(
                """
                {
                    "data": {
                        "id": 1,
                        "accountId": 1,
                        "amount": 10.00,
                        "createdAt": "2020-03-13T12:11:10",
                        "updatedAt": "2020-03-14T13:12:11"
                    }
                }
                """
        )
    }
}
