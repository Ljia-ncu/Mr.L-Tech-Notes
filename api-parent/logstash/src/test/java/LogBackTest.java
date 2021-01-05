import com.mrl.logback.LogBackApplication;
import com.mrl.logback.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: LogBackTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/11 12:10
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogBackApplication.class)
public class LogBackTest {

    @Autowired
    private TestService testService;

    @Test
    public void test() {
        testService.test();
    }
}
