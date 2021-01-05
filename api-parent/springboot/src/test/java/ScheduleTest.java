import com.mrl.springboot.MySpringBootApplication;
import com.mrl.springboot.component.MyScheduleTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: ScheduleTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/4 16:59
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
public class ScheduleTest {

    @Autowired
    private MyScheduleTask task;

    public static void main(String[] args) {
    }

    @Test
    public void test() {

    }
}
