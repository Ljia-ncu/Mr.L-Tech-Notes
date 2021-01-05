import com.mrl.es.ElasticSearchApplication;
import com.mrl.es.entity.Book;
import com.mrl.es.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: ElasticSearchTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/5 15:05
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSearchApplication.class)
public class ElasticSearchTest {

    @Autowired
    private BookService bookService;

    @Test
    public void test() {
        bookService.importData();
    }

    @Test
    public void test2() {
        // 注意Jpa 页码从0开始
        Page<Book> page = bookService.search("mysql", 0, 2);
        List<Book> list = page.getContent();
    }
}
