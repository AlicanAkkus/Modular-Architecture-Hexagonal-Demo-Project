package com.hexagonaldemo.ticketapi;

import org.springframework.boot.actuate.autoconfigure.metrics.orm.jpa.HibernateMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Profile("contractTest")
@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        HibernateMetricsAutoConfiguration.class
})
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*JpaRepository.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Service.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Adapter.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Converter.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Fake.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*TicketApiApplication.*")
        }
)
public class TestConfig {

}
