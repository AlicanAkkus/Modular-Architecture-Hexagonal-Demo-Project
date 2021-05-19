package functional.steps

import com.hexagonaldemo.suite.behavior.BehaviorClient
import com.hexagonaldemo.suite.client.BasicStats
import com.hexagonaldemo.suite.functional.World


abstract class AbstractSteps {

    World world

    BehaviorClient client

    BasicStats stats

    Map config

    AbstractSteps() {
        world = world ?: new World()
        client = world.getClient()
        stats = world.getStats()
        config = world.getConfigData()
    }
}
