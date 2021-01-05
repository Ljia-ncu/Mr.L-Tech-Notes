import cn.hutool.core.lang.UUID;
import com.mrl.mq.RocketApplication;
import com.mrl.mq.listener.MqTransactionListenerImpl;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TxMsgTest
 * @Description
 * @Author Mr.L
 * @Date 2021/1/1 17:29
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketApplication.class)
public class TxMsgTest {

    //事务消息解决的问题是：本地事务 + 消息投递 一起执行
    //同步消息解决的问题是：消息一定投递成功

    //e.g. A工行 转账给 B建行
    //同步消息问题：工行系统发送同步消息(B进账)给MQ，ack成功；工行系统给A扣款，此时出现异常扣款失败，但是B已经收到进账消息了；
    //事务消息能够保证A扣款成功和发送B进账消息处于一个事务；

    //事务消息：工行系统发送半消息（B进账）ack成功，回调executeCommit 执行A扣款，扣款成功后提交给MQ；
    //MQ收到commit，就将半消息转为可消费消息，即建行B此时可以进行消费；
    //如果出现扣款异常、ACK丢失、超时，MQ会重试执行checkLocalTransaction，如果在最大重试次数内没能提交，就丢弃该消息；

    //事务消息的三种状态
    //TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息；
    //TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费；
    //TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态；

    @Test
    public void txproducer() throws MQClientException, InterruptedException {
        TransactionMQProducer producer = new TransactionMQProducer("transaction-pg");
        producer.setNamesrvAddr("localhost:9876");
        //设置事务监听器
        producer.setTransactionListener(new MqTransactionListenerImpl());
        //设置check本地事务线程池
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), r -> {
            Thread t = new Thread(r);
            t.setName("transaction-msg-check-thread");
            return t;
        });
        producer.setExecutorService(executorService);
        producer.start();

        String[] tags = {"tagA", "tagB", "tagC"};
        for (int i = 0; i < 6; i++) {
            //发送事务消息
            Message msg = new Message(
                    "transactionTopic",
                    tags[i % 3],
                    UUID.fastUUID().toString(),
                    ("" + i).getBytes());
            //arg 对应 MqTransactionListenerImpl.executeLocalTransaction.Object o
            SendResult result = producer.sendMessageInTransaction(msg, "arg" + i);
            System.out.println(result);
        }
        //在shutdown之前进行阻塞，等待check本地事务ok
        //等待transaction.log 打印End to check prepare message，即可进行消费
        Thread.currentThread().join();
        producer.shutdown();
    }

    @Test
    public void consumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tx-cg");
        consumer.setNamesrvAddr("localhost:9876");

        consumer.subscribe("transactionTopic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                list.forEach(msg -> {
                    System.out.println(msg.getKeys() + "/" + new String(msg.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        Thread.currentThread().join();
    }
}
