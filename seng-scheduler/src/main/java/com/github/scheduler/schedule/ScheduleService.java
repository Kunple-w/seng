package com.github.scheduler.schedule;

import com.github.scheduler.repository.JobDO;
import com.github.seng.core.threadpool.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * schedule service
 * @author qiankewei
 */
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    private WheelTimerHolder wheelTimerHolder;

    private Thread scheduleThread;

    private Thread cacheThread;

    private volatile List<JobDO> cache = new ArrayList<>();

    private volatile boolean isRunning;

    public void start() {
        wheelTimerHolder = new WheelTimerHolder();
        scheduleThread = new NamedThreadFactory("schedule", false).newThread(new Runnable() {
            @Override
            public void run() {
                wheelTimerHolder.start();
                isRunning = true;
                while (isRunning) {
                    doSechedule(wheelTimerHolder.getJobs());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error("thread-{} interrupt exception", scheduleThread.getName(), e);
                    }
                    wheelTimerHolder.updateWheelTimer();
                }
            }
        });
        cacheThread = new NamedThreadFactory("cache", false).newThread(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                while (isRunning) {
                    cache = getJobsFromDB(currentTime);
                    if (!cache.isEmpty()) {
                        Iterator<JobDO> jobDOIterator = cache.iterator();
                        while (jobDOIterator.hasNext()) {
                            JobDO jobDO = jobDOIterator.next();
                            wheelTimerHolder.pushJob(jobDO);
                            jobDOIterator.remove();
                        }
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        logger.error("thread-{} interrupt exception", scheduleThread.getName(), e);
                    }
                    currentTime += 30000;
                }
            }
        });
    }

    private List<JobDO> getJobsFromDB(long time) {
        //todo
        return null;
    }

    public void cancelJob(String jobId) {
        if (cache.isEmpty()) {
            boolean b = cache.removeIf(jobDO -> jobId.equals(jobDO.getJobId()));
            if (!b) {
                wheelTimerHolder.cancelJob(jobId);
            }

        }

    }

    public void stop() {
        isRunning = false;
        if (scheduleThread.isAlive()) {
            scheduleThread.interrupt();
        }
        if (cacheThread.isAlive()) {
            cacheThread.interrupt();
        }
    }

    private void doSechedule(List<JobDO> jobDOS) {

    }
}
