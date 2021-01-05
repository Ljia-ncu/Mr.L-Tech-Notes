import com.mrl.mq.RocketApplication;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: SeqMsgTest
 * @Description
 * @Author Mr.L
 * @Date 2021/1/1 16:03
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketApplication.class)
public class SeqMsgTest {
    //顺序消费，即按照消息的发送顺序来消费，rocketmq可以保证消息有序
    //默认情况下消息会采用round robin轮询方式将消息发送到不同的queue分区，这种情况发送与消费都不能保证有序，见RocketMQTest
    //如果发送与消费参与的queue只有一个，则是全局有序；如果有多个queue参与，则分为分区有序（相对每个queue,消息都是有序的）

    @Test
    public void seqProducer() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("seq-pg");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        //此处的tag仅表示消息的二级分类，以及作为消费者订阅的筛选条件
        //对消息路由到哪个queue还是取决于orderNo，与此处的tag关系不大
        String[] tags = {"tagA", "tagB", "tagC"};

        //模拟订单消息，这里要保证业务逻辑有序
        String[] orderDesc = {"order A begin", "order B begin", "order A pay", "order A finish", "order B pay", "order B finish"};
        Long[] orderNo = {123L, 233L, 123L, 123L, 233L, 233L};

        for (int i = 0; i < 6; i++) {
            Message msg = new Message("OrderTopic", tags[i % 3], orderDesc[i].getBytes());
            //发送时根据orderNo路由到同一个queue中，保证发送消息有序
            SendResult result = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    return list.get((int) ((Long) arg % list.size()));
                }
            }, orderNo[i]); //此处的orderNo[i] 即为 Object arg
            System.out.println(result);
        }
        producer.shutdown();
    }

    @Test
    public void seqConsumer() throws MQClientException, InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("seq-cg");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("OrderTopic", "tagA || tagB || tagC");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                list.forEach(msg -> {
                    System.out.println(msg.getTags() + "/" + new String(msg.getBody()));
                });
                consumeOrderlyContext.setAutoCommit(true);
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        Thread.currentThread().join();
    }
}
