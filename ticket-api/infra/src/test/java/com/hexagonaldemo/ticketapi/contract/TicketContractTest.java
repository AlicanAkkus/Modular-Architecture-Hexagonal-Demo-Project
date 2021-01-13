package com.hexagonaldemo.ticketapi.contract;

import com.hexagonaldemo.ticketapi.contract.base.BaseTicketContract;
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
public class TicketContractTest extends BaseTicketContract {

	@Test
	public void validate_should_buy_ticket() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"accountId\":123,\"eventId\":5,\"count\":2,\"referenceCode\":\"123e4567-e89b-12d3-a456-426614174000\"}");

		// when:
			Response response = given().spec(request)
					.post("/api/v1/tickets");

		// then:
			assertThat(response.statusCode()).isEqualTo(201);

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['data']").field("['id']").isEqualTo(1);
			assertThatJson(parsedJson).field("['data']").field("['accountId']").isEqualTo(123);
			assertThatJson(parsedJson).field("['data']").field("['eventId']").isEqualTo(5);
			assertThatJson(parsedJson).field("['data']").field("['count']").isEqualTo(2);
			assertThatJson(parsedJson).field("['data']").field("['boughtDate']").isEqualTo("2020-01-01T12:12:12");
			assertThatJson(parsedJson).field("['data']").field("['price']").isEqualTo(90.00);
	}

}
