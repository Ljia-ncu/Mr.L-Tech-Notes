public interface RejectedExecutionHandler {
    void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}

/**
 * 丢弃的线程会跑到调用线程（如main线程）中执行run方法;
 * A handler for rejected tasks that runs the rejected task
 * directly in the calling thread of the {@code execute} method,
 * unless the executor has been shut down, in which case the task
 * is discarded.
 */
public static class CallerRunsPolicy implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            r.run();
        }
    }
}

/**
 * 阻塞队列满了后直接抛异常，线程池退出
 * A handler for rejected tasks that throws a
 * {@code RejectedExecutionException}.
 */
public static class AbortPolicy implements RejectedExecutionHandler {99
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                e.toString());
    }
}

/**
 * 静默丢弃
 * A handler for rejected tasks that silently discards the
 * rejected task.
 */
public static class DiscardPolicy implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    }
}

/**
 * e.getQueue().poll() 即丢弃最前面的线程
 * A handler for rejected tasks that discards the oldest unhandled
 * request and then retries {@code execute}, unless the executor
 * is shut down, in which case the task is discarded.
 */
public static class DiscardOldestPolicy implements RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            e.getQueue().poll();
            e.execute(r);
        }
    }
}