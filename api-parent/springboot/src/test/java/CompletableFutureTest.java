import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: CompletableFutureTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/1 14:25
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompletableFutureTest {

    public void test() {
        CompletableFuture<Void> task1 = new CompletableFuture<>();
        task1.thenApply(i -> {

            return null;
        }).exceptionally(e -> {

            return null;
        });
    }
}
