package com.github.scheduler.schedule;

import com.github.scheduler.repository.JobDO;
import com.github.seng.core.threadpool.SengThreadPoolFactory;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class WheelTimerHolder {

    private WheelTimer wheelTimer;

    private ThreadPoolExecutor executor;


    public void start() {
        wheelTimer = WheelTimer.init();
        wheelTimer.setTick(0);
        wheelTimer.setStartTime(System.currentTimeMillis());
        executor = SengThreadPoolFactory.defaultDynamicThreadPool("wheelTimer", false);
    }

    public void updateWheelTimer() {
        int tick = wheelTimer.getTick();
        if (tick >= 59) {
            wheelTimer.setTick(0);
            wheelTimer.setStartTime(System.currentTimeMillis());
        } else {
            wheelTimer.setTick(tick + 1);
        }
    }

    public List<JobDO> getJobs() {
        return wheelTimer.getBuckets().get(wheelTimer.getTick()).getJobDOs();
    }

    public void pushJob(JobDO jobDO) {
        long currentTime = wheelTimer.getStartTime() + (wheelTimer.getTick() + 1) * 1000;
        long diff = jobDO.getNextTriggerTime() - currentTime;
        if (diff <= 0) {
            //todo 立刻触发一次
        } else {
            if (diff < 60000) {
                int index = (int) diff / 1000 + wheelTimer.getTick();
                if (index > 59) {
                    index -= 60;
                }
                HashedWheelBucket hashedWheelBucket = wheelTimer.getBuckets().get(index);
                hashedWheelBucket.getJobDOs().add(jobDO);
                jobDO.setHashedWheelBucket(hashedWheelBucket);
            }
        }

    }

    public void stop() {
        executor.shutdown();
    }

    public boolean cancelJob(JobDO jobDO) {
        return jobDO.getHashedWheelBucket().getJobDOs().remove(jobDO);
    }
}
