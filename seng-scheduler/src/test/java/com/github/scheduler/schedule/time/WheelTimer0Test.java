package com.github.scheduler.schedule.time;

import com.github.scheduler.repository.JobDO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WheelTimer0Test {
    private static final Logger logger = LoggerFactory.getLogger(WheelTimer0Test.class);

    @Test
    void start() throws InterruptedException {
        WheelTimer0 wheelTimer0 = new WheelTimer0();
        Runnable runnable = () -> {
            logger.info("执行任务成功");
        };
        JobDO jobDO = new JobDO();
        jobDO.setJobId("test-1");

        jobDO.setNextTriggerTime(System.currentTimeMillis() + 2000);
//        jobDO.setLastTriggerTime();
        jobDO.setCommand(runnable);
        wheelTimer0.loadData(jobDO);
        wheelTimer0.setThreadPoolExecutor((ThreadPoolExecutor) Executors.newFixedThreadPool(2));
        wheelTimer0.start();
        TimeUnit.MINUTES.sleep(3);
    }

    @Test
    void stop() {
    }
}