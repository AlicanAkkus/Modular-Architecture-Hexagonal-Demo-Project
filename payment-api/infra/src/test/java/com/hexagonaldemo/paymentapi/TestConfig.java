package com.hexagonaldemo.paymentapi;

import com.hexagonaldemo.paymentapi.common.DomainComponent;
import org.springframework.boot.actuate.autoconfigure.metrics.orm.jpa.HibernateMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("contractTest")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        HibernateMetricsAutoConfiguration.class,
        RedisAutoConfiguration.class
})
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Adapter.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Redis.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*JpaRepository.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Fake.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*PaymentApiApplication.*")
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
        }
)
public class TestConfig {

}
