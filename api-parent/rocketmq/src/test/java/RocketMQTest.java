import com.mrl.mq.RocketApplication;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: RocketMQTest
 * @Description 参考官网做的测试，可直接通过rocketmqTemplate
 * @Author Mr.L
 * @Date 2021/1/1 12:05
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketApplication.class)
public class RocketMQTest {

    @Test
    public void Producer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-pg");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 50; i++) {
            Message msg = new Message(
                    "topic",
                    "tag",
                    "orderId:7456",
                    ("msg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            //设置延时等级为3 即10s;
            //共18个等级：1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
            //场景：如提交一个订单，再发送一个延时消息 用于30min后检测订单支付状态
            //msg.setDelayTimeLevel(5);

            //设置消息属性
            msg.putUserProperty("k", "v");

            //同步发送
            SendResult result = producer.send(msg);
            System.out.println(result);

            //异步发送
            /*producer.setRetryTimesWhenSendAsyncFailed(0);
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
            //等待异步发送完成
            //使用的rocketmq的CountDownLatch2实现，每次回调等待1s
            new CountDownLatch2(100).await(1, TimeUnit.SECONDS);*/

            //单向发送，不关心发送结果，如发送日志信息
            //producer.sendOneway(msg);
        }
        producer.shutdown();
    }

    @Test
    public void consumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-cg");
        consumer.setNamesrvAddr("localhost:9876");

        //subExp * 订阅所有,或者基于表达式
        //consumer.subscribe("OrderTopic", MessageSelector.bySql("a between 0 and 3"));

        consumer.subscribe("topic", "tag");
        //注册监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                list.forEach(msg -> {
                    System.out.println(msg.getKeys() + new String(msg.getBody()) + msg.getUserProperty("k"));
                });
                //标记该消息被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        //阻塞主线程退出
        Thread.currentThread().join();
    }
}
