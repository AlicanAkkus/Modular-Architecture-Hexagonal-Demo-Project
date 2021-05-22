import ch.qos.logback.classic.encoder.PatternLayoutEncoder
statusListener(OnConsoleStatusListener)

appender('CONSOLE', ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
  }
}

logger 'groovy.net.http.JavaHttpBuilder', INFO

root INFO, ['CONSOLE']