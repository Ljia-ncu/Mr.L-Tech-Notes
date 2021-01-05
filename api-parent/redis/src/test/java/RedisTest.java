import com.mrl.redis.RedisApplication;
import com.mrl.redis.entity.Goods;
import com.mrl.redis.entity.Order;
import com.mrl.redis.util.ConstantUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: Test
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 11:14
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        Goods g1 = new Goods().setId(10).setPrice(new BigDecimal("99.99")).setStock(100).setUrl("baidu.com");
        Goods g2 = new Goods().setId(12).setPrice(new BigDecimal("2.56")).setStock(80).setUrl("leetcode.dom");
        redisTemplate.opsForHash().put(ConstantUtil.GOODS_PREFIX.getKey(), "" + g1.getId(), g1);
        redisTemplate.opsForHash().put(ConstantUtil.GOODS_PREFIX.getKey(), "" + g2.getId(), g2);
        ArrayList<Goods> list = new ArrayList<>();
        list.add(g1);
        list.add(g2);
        Order order = new Order().setOrderNo("7456").setDate(new Date()).setRemark("ok").setGoods(list);
        redisTemplate.opsForHash().put(ConstantUtil.ORDER_PREFIX.getKey(), order.getOrderNo(), order);
    }

    @Test
    public void test2() {
        Order order = (Order) redisTemplate.opsForHash().get(ConstantUtil.ORDER_PREFIX.getKey(), "7456");
        System.out.println(order);
    }

    @Test
    public void test3() {
        BoundHashOperations<String, String, Object> operations = redisTemplate.boundHashOps(ConstantUtil.GOODS_PREFIX.getKey());
        Goods g = (Goods) operations.get("12");
        System.out.println(g);
    }

    @Test
    public void test4() {
        redisTemplate.opsForValue().set("nums", 0);
        redisTemplate.opsForValue().increment("nums", 1);
        stringRedisTemplate.opsForValue().increment("nums", 1);
    }

    @Test
    public void test5() {
        Set<ZSetOperations.TypedTuple<Object>> info = new HashSet<>();
        info.add(new DefaultTypedTuple<>("mrl", 18952.0));
        info.add(new DefaultTypedTuple<>("tmp", 11000.0));
        redisTemplate.opsForZSet().add(ConstantUtil.SOCRE_PREFIX.getKey(), info);
        Set<Object> set = redisTemplate.opsForZSet().rangeByScore(ConstantUtil.SOCRE_PREFIX.getKey(), 10000.0, 12000.0);
    }
}
