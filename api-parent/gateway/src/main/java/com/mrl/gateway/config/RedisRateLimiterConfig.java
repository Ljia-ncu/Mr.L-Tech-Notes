package com.mrl.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @ClassName: RedisRateLimiterConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/15 19:11
 * @Version 1.0
 */
@Configuration
public class RedisRateLimiterConfig {

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
