package com.mrl.mq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MqTransactionListenerAnno
 * @Description
 * @Author Mr.L
 * @Date 2021/1/3 3:28
 * @Version 1.0
 */
@Component
@RocketMQTransactionListener
public class MqTransactionListenerAnno implements RocketMQLocalTransactionListener {
    /**
     * 模拟db数据
     */
    ConcurrentHashMap<String, Boolean> db = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String count = new String((byte[]) message.getPayload());
        String orderNo = (String) message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.KEYS);

        //模拟扣减库存后的状态
        db.put(orderNo, (Integer.parseInt(count) % 2) == 0);

        //可以直接设置为COMMIT_MESSAGE，这里用UNKNOW来测试checkLocalTransaction;
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String orderNo = (String) message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.KEYS);
        //显示check逻辑
        boolean status = db.get(orderNo);
        System.out.println((status ? "checked:" : "uncheck:") + orderNo);
        return status ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.UNKNOWN;
    }
}
