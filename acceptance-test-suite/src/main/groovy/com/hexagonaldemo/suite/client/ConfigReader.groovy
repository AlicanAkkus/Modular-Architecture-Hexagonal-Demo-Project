package com.hexagonaldemo.suite.client

import groovy.json.JsonSlurper
import org.springframework.core.io.ClassPathResource

class ConfigReader {

    public static final String CONFIG_FILE = "config.json"
    JsonSlurper slurper
    def config

    ConfigReader() {
        slurper = new JsonSlurper()
        config = slurper.parse(new ClassPathResource(CONFIG_FILE).getFile())
    }
}
