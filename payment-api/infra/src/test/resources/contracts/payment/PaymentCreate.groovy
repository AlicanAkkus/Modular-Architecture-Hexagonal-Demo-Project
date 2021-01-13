package payment

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "create payment"
    description "should create payment"

    request {
        url "/api/v1/payments"
        headers {
            header(contentType(), applicationJson())
        }
        method POST()
        body(
                """
                {
                    "accountId": 1,
                    "price": 10.00,
                    "referenceCode": "ref1"
                }
                """
        )
    }

    response {
        status CREATED()
        headers {
            header(contentType(), applicationJson())
        }
        body(
                """
                {
                    "data": {
                        "id": 1,
                        "accountId": 1,
                        "price": 10.00,
                        "referenceCode": "ref1"
                    }
                }
                """
        )
    }
}