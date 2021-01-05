import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

/**
 * @ClassName: ThreadTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/12 2:45
 * @Version 1.0
 */
@SpringBootTest
public class ThreadPoolTest {

    /**
     * 测试不同的拒接策略
     */
    @Test
    public void test() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 3, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.submit(() -> {
                //如果是CallerRunsPolicy,这里是主线程调用线程池，所以拒绝的run方法会跑到主线程执行，所以此时ThreadName是main；
                System.out.println(Thread.currentThread().getName() + "/" + finalI);
            });
        }

        Thread.currentThread().join();
    }

    //newFixedThreadPool
    //corePoolSize = maxPoolSize keepAliveTime = 0
    //LinkedBlockingQueue DefaultThreadFactory AbortPolicy()
    @Test
    public void fixedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.submit(() -> {
                //ThreadName只有3种，即3个核心线程
                System.out.println(Thread.currentThread().getName() + "/" + finalI);
            });
        }

        Thread.currentThread().join();
    }

    //newSingleThreadExecutor
    //corePoolSize = maxPoolSize = 1, keepAliveTime = 0
    //LinkedBlockingQueue DefaultThreadFactory AbortPolicy()
    @Test
    public void singleThreadPool() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            service.submit(() -> {
                //可以发现 ThreadName都是同一个线程，所以singleThreadPool主要用于顺序执行
                System.out.println(Thread.currentThread().getName() + "/" + finalI);
            });
        }

        Thread.currentThread().join();
    }

    //newCachedThreadPool 它的KeepliveTime是60s，所以可以复用内存中的work线程
    //corePoolSize = 0,maxPoolSize = Int.max,keepAliveTime = 60s
    //SynchronousQueue DefaultThreadFactory AbortPolicy()
    @Test
    public void cacheThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.submit(() -> {
                //这里的ThreadName出现了20多个，即开启了20+个线程
                //主要是由于SynchronousQueue的阻塞机制，以及keepAliveTime=60s,使得内存中的线程能够进行复用
                System.out.println(Thread.currentThread().getName() + "/" + finalI);
            });
        }
        Thread.currentThread().join();
    }

    //newScheduledThreadPool
    //corePoolSize maxPoolSize = Int.max,keepAliveTime = 0
    //DelayedWorkQueue DefaultThreadFactory AbortPolicy()
    @Test
    public void scheduleThreadPool() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        //see ScheduledThreadPoolExecutor.scheduleAtFixedRate
        //ScheduledFutureTask的period值对应不同的调度，这里在对应的run方法执行的是重置过期时间，并重新加入队列
        //线程池在执行时，worker线程会调用任务的run方法
        //另外用的阻塞队列是DelayedWorkQueue，其实就是一个用过期时间作为比较值的优先队列，所以每次加入任务后，就获取队首过期节点执行；
        //至于延时队列是怎么进行延时的，就是通过take的循环操作peek队首，只要队首节点过期就直接poll;否则就通过LockSupport.park进行挂起等待
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName());
        }, 1, 3, TimeUnit.SECONDS);

        Thread.currentThread().join();
    }

    //newWorkStealingPool
    //return new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
    //                      ForkJoinPool.defaultForkJoinWorkerThreadFactory,null, true);
    @Test
    public void workStealingPool() throws InterruptedException {
        ExecutorService service = Executors.newWorkStealingPool(3);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            System.out.println(1);
            service.submit(() -> {
                //守护线程
                System.out.println(Thread.currentThread().getName() + "/" + Thread.currentThread().isDaemon() + "/" + finalI);
            });
        }
        Thread.currentThread().join();
    }
}
