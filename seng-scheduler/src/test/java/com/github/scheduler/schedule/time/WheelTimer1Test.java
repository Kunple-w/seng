package com.github.scheduler.schedule.time;

import com.github.seng.core.threadpool.SengThreadPoolFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

class WheelTimer1Test {

    @Test
    public void test() {
        Callable<Long> callable = new Callable() {
            @Override
            public Long call() throws Exception {
                long time = System.currentTimeMillis();
                System.out.println("执行任务时间： " + time);
                return time;
            }
        };
        ThreadPoolExecutor threadPoolExecutor = SengThreadPoolFactory.defaultFixedThreadPool("timerTest", false);

        List<TimerTask> timerTasks = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            TimerTask timerTask = new TimerTask(UUID.randomUUID().toString(), callable, threadPoolExecutor, (System.currentTimeMillis() + 30000));
            timerTasks.add(timerTask);
        }
        WheelTimer1 wheelTimer1 = new WheelTimer1(timerTasks);
        wheelTimer1.start();


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Callable<Long> callable = new Callable() {
                    @Override
                    public Long call() throws Exception {
                        long time = System.currentTimeMillis();
                        System.out.println("执行任务时间： " + time);
                        return time;
                    }
                };
                ThreadPoolExecutor threadPoolExecutor = SengThreadPoolFactory.defaultFixedThreadPool("timerTest", false);
                while(true) {
                    List<TimerTask> timerTasks = new ArrayList<>(20);
                    for (int i = 0; i < 20; i++) {
                        TimerTask timerTask = new TimerTask(UUID.randomUUID().toString(), callable, threadPoolExecutor, (System.currentTimeMillis() + 30000));
                        timerTasks.add(timerTask);
                    }
                    wheelTimer1.loadData(timerTasks);
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt.");
                    }

                }

            }
        }).start();*/
    }

}