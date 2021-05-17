package com.hexagonaldemo.paymentapi.contract;

import com.hexagonaldemo.paymentapi.TestApplication;
import com.hexagonaldemo.paymentapi.balance.BalanceCompensateCommandHandler;
import com.hexagonaldemo.paymentapi.balance.BalanceRetrieveCommandHandler;
import com.hexagonaldemo.paymentapi.balance.BalanceTransactionCreateCommandHandler;
import com.hexagonaldemo.paymentapi.payment.PaymentCreateCommandHandler;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("contractTest")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TestApplication.class)
@TestPropertySource(properties = "spring.profiles.active=contractTest")
public abstract class AbstractContractTest {

    @LocalServerPort
    private Integer port;

    @MockBean
    protected BalanceTransactionCreateCommandHandler balanceTransactionCreateCommandHandler;

    @MockBean
    protected BalanceRetrieveCommandHandler balanceRetrieveCommandHandler;

    @MockBean
    protected BalanceCompensateCommandHandler balanceCompensateCommandHandler;

    @MockBean
    protected PaymentCreateCommandHandler paymentCreateCommandHandler;

    @BeforeEach
    public void doBeforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        setUp();
    }

    abstract void setUp();
}