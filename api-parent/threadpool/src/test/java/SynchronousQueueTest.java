import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.SynchronousQueue;

/**
 * @ClassName: SynchronousQueueTest
 * @Description
 * @Author Mr.L
 * @Date 2021/1/4 13:12
 * @Version 1.0
 */
@SpringBootTest
public class SynchronousQueueTest {

    /*public SynchronousQueue(boolean fair) {
        transferer = fair ? new SynchronousQueue.TransferQueue<E>() : new SynchronousQueue.TransferStack<E>();
    }*/

    @Test
    public void queue() throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                queue.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.currentThread().join();
    }
}
