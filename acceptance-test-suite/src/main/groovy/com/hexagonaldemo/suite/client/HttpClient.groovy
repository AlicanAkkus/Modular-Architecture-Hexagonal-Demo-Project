package com.hexagonaldemo.suite.client

import groovy.json.JsonGenerator
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import groovyx.net.http.HttpBuilder
import org.apache.commons.codec.Charsets

@Slf4j
class HttpClient {

    Stats stats
    def configData

    private static String profile

    HttpClient(Stats stats, configData) {
        profile = System.getProperty("profile") ?: "local"
        this.stats = stats
        this.configData = configData
    }

    def call(String name, String method, HashMap args) {
        def startTime = System.nanoTime()

        log.info("Request for $name: ${toJson(args)}")
        def response = proceed(name, method, args)

        def endTime = System.nanoTime()
        def execTime = ((endTime - startTime) / 1000000) as Double
        stats.incrementCount()
        stats.keepExecTime(name, execTime)

        log.info("Response for $name: ${toJson(response)}")
        return response
    }

    private proceed(name, method, args) {
        try {
            def httpBuilder = createHttpBuilder(name, args)
            return httpBuilder."${method.toLowerCase()}"() ?: [:]
        } catch (Exception e) {
            if (exceptionBodyExists(e)) {
                def error = getErrorBody(e)
                log.error("[${e.fromServer.statusCode}] [${e}] Http error occurred during $method request: {}", error)
                return e.body
            }
            throw e
        }
    }

    private createHttpBuilder(name, args) {
        String apiName = name.tokenize("_")[0]
        String gatewayUri = this.configData."$apiName"."$profile".baseUri

        println("${gatewayUri}${args.path}")
        return HttpBuilder.configure {
            request.uri = gatewayUri + args.path
            request.uri.path = args.path

            if (args?.query) request.uri.query = args.query
            if (args?.header)  args.header.each { headerName, value -> request.headers."$headerName" = "$value" }
            if (args?.body) request.body = args.body

            request.contentType = args?.header?."Content-Type" ?: "application/json"
            request.charset = Charsets.UTF_8
        }
    }

    // ============================
    // HELPER METHODS
    // ============================

    private String getErrorBody(Exception e) {
        e.body ? e.body instanceof byte[] ? new String(e.body) : e?.body?.errors ? "[${e.body.errors.errorCode}] [${e.body.errors.errorDescription}]" : e : "UNDEFINED"
    }

    private exceptionBodyExists(Exception e) {
        return e.metaClass.getMethods().find { it.name == "getBody" } != null
    }

    private toJson(Map map) {
        if (!map) return "NO DATA"
        return toJson(new JsonGenerator.Options()
                .disableUnicodeEscaping()
                .build()
                .toJson(map))
    }

    private toJson(String str) {
        if (!str) return "NO DATA"
        try {
            return JsonOutput.prettyPrint(str)
        } catch (e) {
            log.error("Exception occurred while converting data into json", e)
            return str
        }
    }
}
