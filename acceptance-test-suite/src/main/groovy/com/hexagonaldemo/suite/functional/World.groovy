package com.hexagonaldemo.suite.functional

import com.hexagonaldemo.suite.behavior.BehaviorClient
import com.hexagonaldemo.suite.client.BasicStats
import com.hexagonaldemo.suite.client.ConfigReader
import com.hexagonaldemo.suite.client.HttpClient


class World {

    private static def data = [:]
    private BehaviorClient client
    private BasicStats stats
    private HttpClient httpClient
    private ConfigReader configReader
    private def configData

    World() {
        stats = new BasicStats()
        configReader = new ConfigReader()
        configData = configReader.config
        httpClient = new HttpClient(stats, configData)
        client = new BehaviorClient(httpClient)
    }

    BehaviorClient getClient() {
        return client
    }

    BasicStats getStats() {
        return stats
    }

    HttpClient getHttpClient() {
        return httpClient
    }

    def getConfigData() {
        return configData
    }

    static keep(key, value) {
        return data.put(key.toString(), value)
    }

    static get(key) {
        return data.get(key.toString())
    }
}
