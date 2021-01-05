package com.mrl.rabbitmq.config;

/**
 * @ClassName: RabbitMqConfig
 * @Description 绑定配置, 或直接在@Listener中配置
 * @Author Mr.L
 * @Date 2020/12/9 1:20
 * @Version 1.0
 */
//@Configuration
public class RabbitMqConfig {

    /*@Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(QueueConstant.QUEUE_TEST_EXCHANGE)
                .durable(true).build();
    }

    @Bean
    public Queue queue() {
        return new Queue(QueueConstant.QUEUE_TEST_QUEUE_NAME);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueConstant.QUEUE_TEST_ROUTE_KEY);
    }*/
}
