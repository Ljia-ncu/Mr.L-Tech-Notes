package com.mrl.mq.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MqTransactionListenerImpl
 * @Description 事务监听器
 * 发送半消息成功后，executeLocalTransaction 执行完本地事务，就返回一个事务状态
 * checkLocalTransaction 用来检查本地事务状态
 * @Author Mr.L
 * @Date 2021/1/2 16:17
 * @Version 1.0
 */
public class MqTransactionListenerImpl implements TransactionListener {
    /**
     * 模拟db数据
     */
    ConcurrentHashMap<String, Boolean> db = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String count = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
            String orderNo = message.getKeys();

            //模拟扣减库存后的状态
            db.put(orderNo, (Integer.parseInt(count) % 2) == 0);

            //可以直接设置为COMMIT_MESSAGE，这里用UNKNOW来测试checkLocalTransaction;
            return LocalTransactionState.UNKNOW;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String orderNo = messageExt.getKeys();
        //显示check逻辑
        System.out.println("check:" + orderNo);
        boolean status = db.get(orderNo);
        //如果返回ROLLBACK_MESSAGE则直接删除半消息，这里测试UNKNOW的最大检测次数
        return status ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.UNKNOW;
    }
}
