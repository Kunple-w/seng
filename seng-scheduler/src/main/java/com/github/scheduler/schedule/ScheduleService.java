package com.github.scheduler.schedule;

import com.github.scheduler.repository.JobDO;
import com.github.seng.core.threadpool.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * schedule service
 * @author qiankewei
 */
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    private WheelTimerHolder wheelTimerHolder;

    private Thread scheduleThread;

    private boolean isRunning;

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
    }

    public void cancelJob(String jobId) {

    }

    public void stop() {
        isRunning = false;
        if (scheduleThread.isAlive()) {
            scheduleThread.interrupt();
        }
    }

    private void doSechedule(List<JobDO> jobDOS) {

    }
}
