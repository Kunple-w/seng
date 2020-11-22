package com.github.scheduler.schedule;

import com.github.scheduler.repository.JobDO;

public class WheelTimerHolder {

    private WheelTimer wheelTimer;

    public WheelTimerHolder(WheelTimer wheelTimer) {
        this.wheelTimer = wheelTimer;
    }

    public void start() {
        long currentTime = System.currentTimeMillis();
        wheelTimer.setTick(0);
        wheelTimer.setStartTime(currentTime);
    }

    public void pushJob(JobDO jobDO) {
        long currentTime = wheelTimer.getStartTime() + (wheelTimer.getTick() + 1) * 1000;
        long diff = jobDO.getNextTriggerTime() - currentTime;
        if (diff <= 0) {
            //todo 立刻触发一次
        } else {
            if (diff < 60000) {
                int index = (int)diff / 1000 + wheelTimer.getTick();
                if (index > 59) {
                    index -= 60;
                }
                HashedWheelBucket hashedWheelBucket = wheelTimer.getBuckets().get(index);
                hashedWheelBucket.getJobDOs().add(jobDO);
            }
        }

    }
}
