package com.hexagonaldemo.suite.client

interface Stats {

    def incrementCount()

    def keepExecTime(String name, double execTime)
}