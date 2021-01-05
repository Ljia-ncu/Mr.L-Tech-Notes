import com.mrl.aliyun.AliyunApplication;
import com.mrl.aliyun.service.OssService;
import com.mrl.aliyun.vo.OssUploadParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: OssTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/12 3:24
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AliyunApplication.class)
public class OssTest {

    @Autowired
    private OssService ossService;

    @Test
    public void getUploadParam() {
        OssUploadParam param = ossService.getUploadParam();
    }
}
