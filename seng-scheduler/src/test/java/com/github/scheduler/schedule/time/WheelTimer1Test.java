package com.github.scheduler.schedule.time;

import com.github.seng.core.threadpool.SengThreadPoolFactory;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;


class WheelTimer1Test {


    public static void main(String[] args) {
        WheelTimer1 wheelTimer1 = new WheelTimer1();
        wheelTimer1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Callable<Long> callable = new Callable() {
                    @Override
                    public Long call() throws Exception {
                        long time = System.currentTimeMillis();
//                        System.out.println("执行任务时间： " + time);
                        return time;
                    }
                };
                ThreadPoolExecutor threadPoolExecutor = SengThreadPoolFactory.defaultFixedThreadPool("timerTest", false);
                while(true) {
                    List<TimerTask> timerTasks = new ArrayList<>(200000);
                    for (int i = 0; i < 200000; i++) {
                        TimerTask timerTask = new TimerTask(UUID.randomUUID().toString(), callable, threadPoolExecutor, (System.currentTimeMillis() + RandomUtils.nextInt(0,30000)));
                        timerTasks.add(timerTask);
                    }
                    wheelTimer1.loadTasks(timerTasks);
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt.");
                    }

                }

            }
        }).start();


    }

}