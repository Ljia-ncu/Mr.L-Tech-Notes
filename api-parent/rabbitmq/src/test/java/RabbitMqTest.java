import com.mrl.rabbitmq.RabbitMqApplication;
import com.mrl.rabbitmq.util.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: RabbitMqTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/9 14:46
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplication.class)
public class RabbitMqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sender() throws InterruptedException {
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_TEST_EXCHANGE, QueueConstant.QUEUE_TEST_ROUTE_KEY, "1", message -> {
            //queue只是持久化元数据，还需要将消息进行持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        Thread.currentThread().join();
    }

    @Test
    public void deadLetter() throws InterruptedException {
        rabbitTemplate.convertAndSend("dead.exchange", "ttl.key", "444", message -> {
            //提前过期也会自动转发到死信队列
            message.getMessageProperties().setExpiration("5000");
            return message;
        });
        Thread.currentThread().join();
    }

    @Test
    public void confirm() throws InterruptedException {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                if (b) {
                    log.info("消息成功发送");
                } else {
                    log.info("消息发送失败" + s);
                }
            }
        });
        //发往错误的交换机，发送失败
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_TEST_EXCHANGE + "@error", QueueConstant.QUEUE_TEST_ROUTE_KEY, 7456L);
        //发送成功
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_TEST_EXCHANGE, QueueConstant.QUEUE_TEST_ROUTE_KEY, 7456L);
        Thread.currentThread().join();
    }

    @Test
    public void confirm2() throws InterruptedException {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message msg, int errCode, String errMsg, String exchange, String routingKey) {
                log.info("投递失败" + errMsg);
            }
        });
        //用错误的路由键，发送失败
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_TEST_EXCHANGE, QueueConstant.QUEUE_TEST_ROUTE_KEY + "@err", 7456L);
        //发送成功
        rabbitTemplate.convertAndSend(QueueConstant.QUEUE_TEST_EXCHANGE, QueueConstant.QUEUE_TEST_ROUTE_KEY, 7456L);
        Thread.currentThread().join();
    }
}
