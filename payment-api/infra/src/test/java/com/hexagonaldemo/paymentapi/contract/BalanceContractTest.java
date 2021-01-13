package com.hexagonaldemo.paymentapi.contract;

import com.hexagonaldemo.paymentapi.contract.base.BaseBalanceContractTest;
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
public class BalanceContractTest extends BaseBalanceContractTest {

	@Test
	public void validate_retrieve_balance_by_account_id() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json");

		// when:
			Response response = given().spec(request)
					.queryParam("accountId","1")
					.get("/api/v1/balances");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['data']").field("['id']").isEqualTo(1);
			assertThatJson(parsedJson).field("['data']").field("['accountId']").isEqualTo(1);
			assertThatJson(parsedJson).field("['data']").field("['amount']").isEqualTo(10.00);
			assertThatJson(parsedJson).field("['data']").field("['createdAt']").isEqualTo("2020-03-13T12:11:10");
			assertThatJson(parsedJson).field("['data']").field("['updatedAt']").isEqualTo("2020-03-14T13:12:11");
	}

}
