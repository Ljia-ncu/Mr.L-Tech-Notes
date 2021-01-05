import com.mrl.redis.RedisApplication;
import com.mrl.redis.entity.Goods;
import com.mrl.redis.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @ClassName: SpringCacheTest
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 17:37
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class SpringCacheTest {
    @Autowired
    private TestService service;

    @Test
    public void cacheTest() {
        Goods g = new Goods().setId(10).setPrice(new BigDecimal("99.99")).setStock(100).setUrl("baidu.com");
        service.add(g);

        g.setId(2).setUrl("weibo.com");
        service.put(g);
    }

    @Test
    public void test2() {
        System.out.println(service.get(10));
    }

    @Test
    public void test3() {
        service.deleteOrUpdate(new Goods().setId(2));
    }
}
