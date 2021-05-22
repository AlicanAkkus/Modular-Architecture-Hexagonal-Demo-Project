package contracts.balance

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "create balance by account id"
    description "should create balance by id"

    request {
        url "/api/v1/balances"
        headers {
            header(contentType(), applicationJson())
        }
        method POST()
        body(
                """
                {
                    "accountId": 1,
                    "amount": 10.00,
                    "type": "DEPOSIT"
                }
                """
        )

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
