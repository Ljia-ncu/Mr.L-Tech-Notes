package com.mrl.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DeadLetterConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/9 16:32
 * @Version 1.09
 */
@Configuration
public class DeadLetterConfig {

    /**
     * 延时队列，当消息过期 就通过dead.key -> dead.exchange -> dead.queue
     * x-message-ttl 过期时间
     *
     * @return ttl-queue
     */
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("x-dead-letter-exchange", "dead.exchange");
        map.put("x-dead-letter-routing-key", "dead.key");
        map.put("x-message-ttl", 30000);
        return new Queue("ttl.queue", true, false, false, map);
    }

    /**
     * dead.exchange 通过ttl.key 将消息路由到 ttl.queue
     *
     * @return
     */
    @Bean
    public Binding ttlBinding() {
        return new Binding("ttl.queue", Binding.DestinationType.QUEUE, "dead.exchange", "ttl.key", null);
    }

    /**
     * 连接两个队列的交换机
     *
     * @return
     */
    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange("dead.exchange").durable(true).build();
    }

    /**
     * 死信队列，消费
     *
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return new Queue("dead.queue", true, false, false);
    }

    /**
     * dead.exchange 通过 dead.key 将消息路由到 dead.queue
     *
     * @return
     */
    @Bean
    public Binding deadBinding() {
        return new Binding("dead.queue", Binding.DestinationType.QUEUE, "dead.exchange", "dead.key", null);
    }
}
