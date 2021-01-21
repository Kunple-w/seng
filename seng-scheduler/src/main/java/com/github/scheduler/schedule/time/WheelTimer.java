package com.github.scheduler.schedule.time;

import com.github.seng.core.threadpool.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author qiankewei
 */
public class WheelTimer {
    private static final Logger logger = LoggerFactory.getLogger(WheelTimer.class);
    /**
     * 底层存1分钟的任务，每格代表1s
     */
    private final WheelBucket[] buckets = new WheelBucket[60];

    /**
     * 当前指针指向哪一格
     */
    private AtomicInteger tick;

    /**
     * 当前时间轮是从哪一时刻算起的
     */
    private long startTime;

    private volatile boolean stop = false;

    private Thread tickThread;

    private ConcurrentHashMap<String, Future<?>> resultMap = new ConcurrentHashMap<>();

    public WheelTimer() {
        for (int i = 0; i < 60; i++) {
            buckets[i] = new WheelBucket();
        }
        tick = new AtomicInteger(0);
    }

    public void start() {
        startTime = System.currentTimeMillis();
        tickThread = new NamedThreadFactory("tickThread", false).newThread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    long sleepTime = System.currentTimeMillis() + 1000;
                    buckets[tick.get()].runTask();
                    tick.getAndIncrement();
                    if (tick.get() > 59) {
                        tick.set(0);
                        startTime = System.currentTimeMillis();
                    }
                    sleepTime -= System.currentTimeMillis();
                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            logger.error("tickThread interrupt.", e);
                        }
                    }
                }
            }
        });
        tickThread.start();
    }


    public void stop() {
        stop = true;
        if (tickThread.isAlive()) {
            tickThread.interrupt();
        }
    }

    public void loadTasks(List<TimerTask> tasks) {
        for (TimerTask timerTask : tasks) {
            long diff = timerTask.getTriggerTime() - (startTime + (tick.get() + 1) * 1000);
            if (diff < 0) {
                Future future = timerTask.call();
                resultMap.put(timerTask.getTaskId(), future);
            } else {
                if (diff < 60000) {
                    int index = (int) diff / 1000 + tick.get() + 1;
                    if (index > 59) {
                        index -= 60;
                    }
                    buckets[index].addTask(timerTask);
                }
            }
        }
    }

    public void cancelTask(String taskId) {
        if (resultMap.containsKey(taskId)) {
            resultMap.get(taskId).cancel(true);
        } else {
            for (int i = 0; i < 60; i++) {
                if (buckets[i].removeByTaskId(taskId)) {
                    return;
                }
            }
        }
    }


}
