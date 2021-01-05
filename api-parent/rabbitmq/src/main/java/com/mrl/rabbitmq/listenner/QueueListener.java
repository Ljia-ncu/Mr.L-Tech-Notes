package com.mrl.rabbitmq.listenner;

import com.mrl.rabbitmq.util.QueueConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName: QueueListener
 * @Description
 * @Author Mr.L
 * @Date 2020/12/9 2:26
 * @Version 1.0
 */
@Component
public class QueueListener {
    Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConstant.QUEUE_TEST_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = QueueConstant.QUEUE_TEST_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = {QueueConstant.QUEUE_TEST_ROUTE_KEY}
    ))
    public void listener(Message msg, Channel channel) {
        String str = new String(msg.getBody());
        try {
            //不进行ack，则会重复消费
            //channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
            //doSth
            logger.info("received" + Long.parseLong(str));
        } catch (Exception e) {
            logger.error("failed to ack msg" + str);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {"dead.queue"})
    public void deadListener(Message msg, Channel channel) throws IOException {
        System.out.println(new String(msg.getBody()));
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
