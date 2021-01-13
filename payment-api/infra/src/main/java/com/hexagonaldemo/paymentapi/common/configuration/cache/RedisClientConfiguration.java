package com.hexagonaldemo.paymentapi.common.configuration.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.SocketOptions;
import java.time.Duration;
import java.util.Objects;

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
        RedisStandaloneConfiguration clientConfiguration = new RedisStandaloneConfiguration();
        return new LettuceConnectionFactory(clientConfiguration);
    }
    @Bean
    public RedisLockRegistry accountLockRegistry() {
        return new RedisLockRegistry(redisConnectionFactory(), ACCOUNT_LOCK, accountLockDuration);
    }
}
