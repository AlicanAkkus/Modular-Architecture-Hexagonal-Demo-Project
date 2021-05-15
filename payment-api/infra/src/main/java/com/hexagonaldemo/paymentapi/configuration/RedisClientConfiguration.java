package com.hexagonaldemo.paymentapi.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Slf4j
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
public class RedisClientConfiguration {

    public static final String ACCOUNT_LOCK = "account-lock";
    private Long accountLockDuration = 10000L;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        var clientConfiguration = new RedisStandaloneConfiguration();
        return new LettuceConnectionFactory(clientConfiguration);
    }
    @Bean
    public RedisLockRegistry accountLockRegistry() {
        return new RedisLockRegistry(redisConnectionFactory(), ACCOUNT_LOCK, accountLockDuration);
    }
}
