package com.github.scheduler.schedule.time;

import com.github.scheduler.repository.JobDO;
import com.github.scheduler.schedule.HashedWheelBucket;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyongxu
 */
public class WheelTimer0 {
    private static final Logger logger = LoggerFactory.getLogger(WheelTimer0.class);
    /**
     * 底层存1分钟的任务，每格代表1s
     */
    private final HashedWheelBucket[] buckets = new HashedWheelBucket[60];

    private Set<JobDO> existedJobs = new HashSet<>();

    /**
     * 当前指针指向哪一格
     */
    private int tick;

    /**
     * 当前时间轮是从哪一时刻算起的
     */
    private long startTime;
    /**
     * tick线程
     */
    private final ScheduledExecutorService tickSchedule = Executors.newSingleThreadScheduledExecutor();

    private ThreadPoolExecutor threadPoolExecutor;

    public WheelTimer0() {
        this(Collections.emptyList());
    }


    public WheelTimer0(List<JobDO> jobs) {
        startTime = System.currentTimeMillis();
        tick = 0;
        loadData(jobs);
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public void start() {
        init();
    }

    public void stop() {
        tickSchedule.shutdownNow();
    }


    private void init() {
        if (threadPoolExecutor == null) {
            throw new IllegalArgumentException("executor is null");
        }
        Runnable r = () -> {
            logger.info("1 s");
            if (tick >= 59) {
                tick = 0;
                startTime = (System.currentTimeMillis());
            } else {
                tick++;
            }
            HashedWheelBucket bucket = buckets[tick];
            if (bucket != null) {
                for (JobDO jobDO : bucket.getJobDOs()) {
                    threadPoolExecutor.execute(jobDO.getCommand());
                }
            }
        };
        tickSchedule.scheduleAtFixedRate(r, 0, 1000, TimeUnit.MILLISECONDS);
    }


    public void loadData(JobDO jobDO) {
        long currentTime = startTime + (tick + 1) * 1000;
        long diff = jobDO.getNextTriggerTime() - currentTime;
        if (diff <= 0) {
            //todo 立刻触发一次
            threadPoolExecutor.execute(jobDO.getCommand());
        } else {
            if (diff < 60000) {
                int index = (int) diff / 1000 + tick;
                if (index > 59) {
                    index -= 60;
                }
                HashedWheelBucket hashedWheelBucket = buckets[index];
                if (hashedWheelBucket == null) {
                    buckets[index] = new HashedWheelBucket();
                }
                buckets[index].getJobDOs().add(jobDO);
            }
        }
    }


    public int loadData(List<JobDO> list) {
        Sets.SetView<JobDO> difference = Sets.difference(new HashSet<>(list), existedJobs);
        difference.forEach(this::loadData);
        return difference.size();
    }

    public boolean cancelJob(String jobId) {
        for (int i = tick + 1; i < tick + 59; i++) {
            int index = i;
            if (index > 59) {
                index -= 60;
            }
            JobDO jobDO = buckets[index].ifContains(jobId);
            if (jobDO != null) {
                existedJobs.remove(jobDO);
                return buckets[index].getJobDOs().remove(jobDO);
            }
        }
        return false;
    }

}
