package com.hexagonaldemo.paymentapi.contract;

import com.hexagonaldemo.paymentapi.TestApplication;
import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.balance.BalanceAdminUseCaseHandler;
import com.hexagonaldemo.paymentapi.balance.BalanceCompensateUseCaseHandler;
import com.hexagonaldemo.paymentapi.balance.BalanceRetrieveUseCaseHandler;
import com.hexagonaldemo.paymentapi.balance.BalanceTransactionCreateUseCaseHandler;
import com.hexagonaldemo.paymentapi.payment.PaymentCreateUseCaseHandler;
import com.hexagonaldemo.paymentapi.payment.PaymentRollbackUseCaseHandler;
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
    protected BalanceTransactionCreateUseCaseHandler balanceTransactionCreateUseCaseHandler;

    @MockBean
    protected BalanceRetrieveUseCaseHandler balanceRetrieveUseCaseHandler;

    @MockBean
    protected BalanceCompensateUseCaseHandler balanceCompensateUseCaseHandler;

    @MockBean
    protected PaymentCreateUseCaseHandler paymentCreateUseCaseHandler;

    @MockBean
    protected BalanceAdminUseCaseHandler balanceAdminUseCaseHandler;

    @MockBean
    protected PaymentRollbackUseCaseHandler paymentRollbackUseCaseHandler;

    @MockBean
    protected AccountFacade accountFacade;

    @BeforeEach
    public void doBeforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        setUp();
    }

    abstract void setUp();
}