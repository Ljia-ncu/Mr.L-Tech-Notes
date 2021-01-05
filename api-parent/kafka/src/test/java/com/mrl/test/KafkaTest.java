package com.mrl.test;

import com.mrl.kafka.KafkaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: KafkaTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/22 14:34
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaApplication.class)
public class KafkaTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void send() throws InterruptedException, ExecutionException, TimeoutException {
        kafkaTemplate.send("topic", "ok").get(10, TimeUnit.SECONDS);
        Thread.currentThread().join();
    }

    @Test
    public void sendAsync() {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic", "msg");
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {

            }
        });
    }


}
