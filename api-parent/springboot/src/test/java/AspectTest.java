import com.mrl.springboot.MySpringBootApplication;
import com.mrl.springboot.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: AnnotationTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/1 10:41
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
public class AspectTest {

    @Autowired
    private TestService service;

    @Test
    public void test() throws Exception {
        service.doSth();
        //service.doSth2();
        //service.test();
    }

}
