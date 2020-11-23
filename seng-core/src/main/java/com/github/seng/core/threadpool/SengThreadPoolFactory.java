package com.github.seng.core.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * seng thread pool
 * @author qiankewei
 */
public class SengThreadPoolFactory {

    private static final Logger logger = LoggerFactory.getLogger(SengThreadPoolFactory.class);

    private static final int MAX_THREADS = 10;

    private static final int MIN_THREADS = 5;

    private static final long DEFAULT_KEEP_ALIVE_TIME = 5;

    private static final int DEFAULT_QUEUE_SIZE = 100;

    public static ThreadPoolExecutor dynamicThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String prefix, boolean daemon, RejectedExecutionHandler handler) {
       return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new NamedThreadFactory(prefix, daemon), handler);
    }

    public static ThreadPoolExecutor defaultDynamicThreadPool(String prefix, boolean daemon) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE);
        return new ThreadPoolExecutor(MIN_THREADS, MAX_THREADS, DEFAULT_KEEP_ALIVE_TIME, TimeUnit.MINUTES, workQueue, new NamedThreadFactory(prefix, daemon), new SengRejectedExecutionHandler());
    }

    public static ThreadPoolExecutor fixedThreadPool(int corePoolSize, BlockingQueue<Runnable> workQueue, String prefix, boolean daemon, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS, workQueue, new NamedThreadFactory(prefix, daemon), handler);
    }

    public static ThreadPoolExecutor defaultFixedThreadPool(String prefix, boolean daemon) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE);
        return new ThreadPoolExecutor(MIN_THREADS, MIN_THREADS, 0L, TimeUnit.MILLISECONDS, workQueue, new NamedThreadFactory(prefix, daemon), new SengRejectedExecutionHandler());
    }


    public static class SengRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.warn("Queue buff is full . Discard buffer task->{},pool->{}", r.toString(), executor.toString());
        }
    }

}
