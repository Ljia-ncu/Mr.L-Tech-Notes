package com.mrl.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MqConsumer
 * @Description
 * @Author Mr.L
 * @Date 2021/1/3 0:40
 * @Version 1.0
 */
@Slf4j
@Component
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}",
        topic = "${rocketmq.consumer.topic}", consumerGroup = "${rocketmq.consumer.group}",
        selectorExpression = "${rocketmq.consumer.select-exp}")
public class MqConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt msg) {
        System.out.println(new String(msg.getBody()) + "/"
                + msg.getKeys() + "/"
                + msg.getTags());
    }
}
