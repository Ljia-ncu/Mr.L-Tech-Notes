import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: RedissonTest
 * @Description see https://github.com/redisson/redisson/wiki
 * https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter
 * @Author Mr.L
 * @Date 2020/12/1 10:34
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    private int count = 0;

    @Test
    public void test() {

        RLock lock = redissonClient.getLock("mrl");
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }


        System.out.println(count);
    }
}
