package com.hexagonaldemo.paymentapi.contract;

import com.hexagonaldemo.paymentapi.contract.base.BasePaymentContractTest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.RestAssured.*;

@SuppressWarnings("rawtypes")
public class PaymentContractTest extends BasePaymentContractTest {

	@Test
	public void validate_create_payment() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"accountId\":1,\"price\":10.00,\"referenceCode\":\"ref1\"}");

		// when:
			Response response = given().spec(request)
					.post("/api/v1/payments");

		// then:
			assertThat(response.statusCode()).isEqualTo(201);
			assertThat(response.header("Content-Type")).isEqualTo("application/json");

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['data']").field("['id']").isEqualTo(1);
			assertThatJson(parsedJson).field("['data']").field("['accountId']").isEqualTo(1);
			assertThatJson(parsedJson).field("['data']").field("['price']").isEqualTo(10.00);
			assertThatJson(parsedJson).field("['data']").field("['referenceCode']").isEqualTo("ref1");
	}

}
