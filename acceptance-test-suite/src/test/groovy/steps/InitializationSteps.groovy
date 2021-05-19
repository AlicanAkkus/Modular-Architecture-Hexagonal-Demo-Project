package steps

import groovy.util.logging.Slf4j
import io.cucumber.java.Scenario
import io.cucumber.java.After
import io.cucumber.java.Before

@Slf4j
class InitializationSteps extends AbstractSteps {

    @Before
    def doBefore(Scenario scenario) {
        log.info("============================================")
        log.info("START> SCENARIO: $scenario.name")
        log.info("============================================")

        stats.initialize()
        client.deleteAllBalances()
        client.deleteAllTickets()
        client.deleteAllMeetups()
    }

    @After
    def doAfter(Scenario scenario) {
        log.info("============================================")
        log.info("END> SCENARIO: $scenario.name")
        log.info("============================================")

        stats.printExecutionTimes()
    }
}
