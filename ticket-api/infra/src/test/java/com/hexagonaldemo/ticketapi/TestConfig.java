package com.hexagonaldemo.ticketapi;

import org.springframework.boot.actuate.autoconfigure.metrics.orm.jpa.HibernateMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.cloud.stream.config.BindersHealthIndicatorAutoConfiguration;
import org.springframework.cloud.stream.config.BindingsEndpointAutoConfiguration;
import org.springframework.cloud.stream.config.ChannelBindingAutoConfiguration;
import org.springframework.cloud.stream.config.ChannelsEndpointAutoConfiguration;
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
        HibernateMetricsAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RedisAutoConfiguration.class,
        ChannelBindingAutoConfiguration.class,
        ChannelsEndpointAutoConfiguration.class,
        BindingsEndpointAutoConfiguration.class,
        BindersHealthIndicatorAutoConfiguration.class,
        IntegrationAutoConfiguration.class
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
