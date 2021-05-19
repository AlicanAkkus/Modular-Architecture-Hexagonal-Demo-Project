package functional.steps


import groovy.util.logging.Slf4j
import io.cucumber.java.en.And
import io.cucumber.java.en.Given

@Slf4j
class TicketSteps extends AbstractSteps {

    def toAccountId(String customerName) {
        return customerName.size();
    }

    @And(/^customer (.*) has a balance with (.*) TL$/)
    def check_balance_amount(customerName, balanceAmount) {
        def balance = client.retrieveBalance(toAccountId(customerName));
        assert balance.amount == balanceAmount
    }

    @And(/^a ticket of (.*) is (.*) TL$/)
    def create_meetup(meetupName, ticketPrice) {
    }


    @And(/^customer (.*) tries to buy (.*) tickets from (.*)$/)
    def buy_tickets(customerName, ticketCount, meetupName) {
    }

    @Given(/^customer (.*) can buy tickets$/)
    def validate_buy_tickets(username, courierStates) {
    }

    @Given(/^customer (.*) cannot buy tickets due to (.*)$/)
    def validate_cannot_buy_tickets(username, courierStates) {
    }
}