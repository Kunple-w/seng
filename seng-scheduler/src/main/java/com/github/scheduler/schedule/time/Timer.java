package com.github.scheduler.schedule.time;

import com.github.scheduler.repository.JobDO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyongxu
 */
public class Timer {
    private WheelTimer0 wheelTimer0 = new WheelTimer0();
    private Map<JobDO, Runnable> runnables = new HashMap<>();

    public void start() {
        wheelTimer0.start();
    }

    void schedule(JobDO jobDO, Runnable runnable) {
        wheelTimer0.loadData(jobDO);
    }
}
