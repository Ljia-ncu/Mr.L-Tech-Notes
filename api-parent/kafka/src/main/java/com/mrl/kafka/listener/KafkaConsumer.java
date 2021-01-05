package com.mrl.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: KafkaConsumer
 * @Description
 * @Author Mr.L
 * @Date 2020/12/22 14:41
 * @Version 1.0
 */
@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"topic"}, groupId = "consumer_group")
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
    }
}
