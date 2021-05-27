package steps

import com.hexagonaldemo.suite.client.BasicStats
import groovy.util.logging.Slf4j
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.And
import org.junit.AfterClass
import org.junit.BeforeClass

@Slf4j
class InitializationSteps extends AbstractSteps {

    @Before
    def doBefore(Scenario scenario) {
        log.info("============================================")
        log.info("START> SCENARIO: $scenario.name")
        log.info("============================================")

        client.deleteAllBalances()
        client.deleteAllTickets()
        client.deleteAllMeetups()
    }

    @And(/^initialize test suite$/)
    def doBeforeAll() {
        log.info("============================================")
        log.info("START> SCENARIOS")
        log.info("============================================")

        BasicStats.initialize()
    }

    @After
    def doAfter(Scenario scenario) {
        log.info("============================================")
        log.info("END> SCENARIO: $scenario.name")
        log.info("============================================")
    }

    @And(/^complete test suite$/)
    def doAfterAll() {
        log.info("============================================")
        log.info("END> SCENARIOS")
        log.info("============================================")

        BasicStats.printExecutionTimes()
    }

}
