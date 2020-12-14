package com.github.scheduler.schedule.time;

import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 *
 * @author qiankewei
 * @date 2020/12/14 16:51
 * @version v1.0.0
 */

@Data
public class TimerTask<T> {
    private String taskId;

    private Callable task;

    private ExecutorService executorService;

    private long executeTime;

    public TimerTask (String taskId, Callable task, ExecutorService executorService, long executeTime) {
        this.taskId = taskId;
        this.task = task;
        this.executorService = executorService;
        this.executeTime = executeTime;
    }

    public Future<T> call() {
        Future<T> future = executorService.submit(task);
        System.out.println("该任务" + taskId +"预计执行时间： " + executeTime + ", 实际执行时间： " + System.currentTimeMillis());
        return future;
    }
}
