import cn.hutool.core.lang.UUID;
import com.mrl.mq.RocketApplication;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: RocketMQTemplateTest
 * @Description
 * @Author Mr.L
 * @Date 2021/1/3 0:35
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketApplication.class)
public class RocketMQTemplateTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void syncSend() throws InterruptedException {
        //SpringMessage To RocketMQMessage
        Message<String> msg = MessageBuilder.withPayload("ok").setHeader(RocketMQHeaders.KEYS, "v").build();

        //通过topic:tag形式可指定tag
        SendResult result = template.syncSend("anno-topic:tagA", msg);
        System.out.println(result);

        Thread.currentThread().join();
    }

    @Test
    public void seqSend() throws InterruptedException {
        String[] tags = {"tagA", "tagB", "tagC"};

        String[] orderDesc = {"order A begin", "order B begin", "order A pay", "order A finish", "order B pay", "order B finish"};
        Long[] orderNo = {123L, 233L, 123L, 123L, 233L, 233L};

        for (int i = 0; i < 6; i++) {
            Message<String> msg = MessageBuilder.withPayload(orderDesc[i]).setHeader(RocketMQHeaders.KEYS, "" + i).build();
            SendResult result = template.syncSendOrderly("anno-topic:" + tags[i % 3], msg, orderNo[i] + "");
            System.out.println(result);
        }

        Thread.currentThread().join();
    }

    @Test
    public void txSend() throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            //注意转成字节数组
            Message msg = MessageBuilder.withPayload(("" + i).getBytes()).setHeader(RocketMQHeaders.KEYS, UUID.fastUUID().toString()).build();
            SendResult result = template.sendMessageInTransaction("anno-topic-tx:tagA", msg, i);
            System.out.println(result);
        }

        Thread.currentThread().join();
    }

}
