package steps

import groovy.util.logging.Slf4j
import io.cucumber.java.en.And

import static com.hexagonaldemo.suite.functional.World.get
import static com.hexagonaldemo.suite.functional.World.keep

@Slf4j
class TicketSteps extends AbstractSteps {

    def toAccountId(String customerName) {
        return customerName.size();
    }

    @And(/^customer (.*) has a balance with (.*) TL$/)
    def check_balance_amount(customerName, double balanceAmount) {
        def accountId = toAccountId(customerName)

        def balance = client.retrieveBalance(accountId);
        assert balance.amount == balanceAmount
    }

    @And(/^customer (.*) initiated a balance with (.*) TL$/)
    def create_balance(customerName, double balanceAmount) {
        def accountId = toAccountId(customerName)

        def balance = client.retrieveBalance(accountId);
        assert balance != null

        balance = client.deposit(accountId, balanceAmount)
        assert balance.amount == balanceAmount
    }

    @And(/^(.*) will be on (.*) with a ticket price (.*) TL$/)
    def create_meetup(meetupName, meetupDate, ticketPrice) {
        def meetup = client.createMeetup(meetupName, ticketPrice, meetupDate)
        keep("$meetupName", meetup.id)
    }

    @And(/^customer (.*) tries to buy (.*) tickets from (.*)$/)
    def buy_tickets(customerName, ticketCount, meetupName) {
        def response = client.reserveTicket(toAccountId(customerName), get("$meetupName"), ticketCount)
        if (response.errors != null) keep("reserveErrorCode", response.errors.errorCode)
    }

    @And(/^customer (.*) can buy tickets$/)
    def validate_buy_tickets(customerName) {
        def accountId = toAccountId(customerName)
        def tickets = client.retrieveTickets(accountId)

        assert tickets.size() == 1
        assert tickets[0].accountId == accountId
    }

    @And(/^customer (.*) cannot buy tickets due to error code (.*)$/)
    def validate_cannot_buy_tickets(customerName, errorCode) {
        assert get("reserveErrorCode") == "$errorCode"
        assert client.retrieveTickets(toAccountId(customerName)).size() == 0
    }
}