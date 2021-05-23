package com.hexagonaldemo.ticketapi.contract;

import com.hexagonaldemo.ticketapi.TestApplication;
import com.hexagonaldemo.ticketapi.meetup.MeetupCreateCommandHandler;
import com.hexagonaldemo.ticketapi.reservation.TicketReserveCommandHandler;
import com.hexagonaldemo.ticketapi.reservation.port.TicketReservedEventPort;
import com.hexagonaldemo.ticketapi.ticket.TicketRetrieveCommandHandler;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Tag("contractTest")
@AutoConfigureMockMvc
@AutoConfigureMessageVerifier
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TestApplication.class)
@TestPropertySource(properties = "spring.profiles.active=contractTest")
public abstract class AbstractContractTest {

    @LocalServerPort
    private Integer port;

    @MockBean
    protected TicketReserveCommandHandler ticketReserveCommandHandler;

    @MockBean
    protected MeetupCreateCommandHandler meetupCreateCommandHandler;

    @MockBean
    protected TicketRetrieveCommandHandler ticketRetrieveCommandHandler;

    @Autowired
    protected TicketReservedEventPort ticketReservedEventPort;

    @BeforeEach
    public void doBeforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        setUp();
    }

    abstract void setUp();
}
