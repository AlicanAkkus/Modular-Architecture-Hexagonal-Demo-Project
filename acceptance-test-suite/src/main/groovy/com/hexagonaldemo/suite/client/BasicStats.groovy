package com.hexagonaldemo.suite.client

import groovy.util.logging.Slf4j

import java.text.DecimalFormat
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Slf4j
class BasicStats implements Stats {

    private static AtomicInteger httpCallCount = new AtomicInteger(0)
    private static ConcurrentHashMap httpCalls = [:]

    def static initialize() {
        httpCalls = [:]
    }

    def incrementCount() {
        httpCallCount.incrementAndGet()
    }

    def static printExecutionTimes() {
        log.info("[HTTP CALLS EXECUTION TIMES] Stats for total ${httpCallCount.get()} calls is listed as follows:")

        log.info("-" * 128)
        log.info(String.format("| %52s | %-15s | %-15s | %-15s | %-15s |", "NAME", "MAX", "MIN", "AVG", "COUNT"))

        log.info("-" * 128)
        printExecutionTime().each { log.info(it) }
        log.info("-" * 128)
    }

    def keepExecTime(String name, double execTime) {
        httpCalls."$name" = httpCalls."$name" ?: ["min": Long.MAX_VALUE, "max": Long.MIN_VALUE, "total": 0.0, count: 0]

        def nameMap = httpCalls."$name"
        if (execTime > nameMap.max) nameMap.max = execTime
        if (execTime < nameMap.min) nameMap.min = execTime
        nameMap.count = nameMap.count + 1
        nameMap.total = nameMap.total + execTime
    }

    private static List<String> printExecutionTime() {
        return httpCalls.toSorted { a, b -> -(a.value.total / a.value.count) <=> (b.value.total / b.value.count) }.collect { key, value ->
            String.format("| %52s | %-15s | %-15s | %-15s | %-15s |",
                    key,
                    "${formatDouble(value.max)} ms",
                    "${formatDouble(value.min)} ms",
                    "${formatDouble(value.total / value.count)} ms",
                    "$value.count")
        }
    }

    static String formatDouble(double execTime) {
        new DecimalFormat("#.00").format(execTime)
    }
}
