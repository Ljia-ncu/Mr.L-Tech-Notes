import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrl.mybatisplus.MyBatisPlusApplication;
import com.mrl.mybatisplus.entity.Info;
import com.mrl.mybatisplus.mapper.InfoMapper;
import com.mrl.mybatisplus.vo.InfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: MyBatisTest
 * @Description
 * @Author Mr.L
 * @Date 2020/11/28 23:02
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisPlusApplication.class)
public class MyBatisPlusTest {

    @Autowired
    private InfoMapper infoMapper;

    @Test
    public void test() {

        Info info = new Info().setName("mrl").setAge(18).setAccount(new BigDecimal(100));
        infoMapper.insert(info);
    }

    @Test
    public void test2() throws InterruptedException {
        //乐观锁更新时需要带上version字段
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Info info = infoMapper.selectById(1);
                if (infoMapper.updateById(info) != 1) {
                    System.out.println("fail to get lock");
                } else {
                    System.out.println("success in getting lock " + info.getVersion());
                }
            }).start();
        }
        Thread.currentThread().join();
    }

    @Test
    public void test3() {
        Page<Info> page = infoMapper.selectPage(new Page<>(1, 2, false),
                Wrappers.<Info>lambdaQuery().eq(Info::getName, "mrl"));
        System.out.println(page.getRecords().size());
    }

    @Test
    public void test4() {
        List<InfoVo> vo = infoMapper.selectByAge(19);
        List<InfoVo> vo2 = infoMapper.selectByName("mrl");
        System.out.println(vo2.size());
    }

    @Test
    public void test5() {
        infoMapper.deleteById(1);
        System.out.println(null == infoMapper.selectById(1));
    }
}
