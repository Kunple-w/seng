package com.github.scheduler.schedule.time;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author qiankewei
 */

@Data
public class TimerTask<T> {
    private static final Logger logger = LoggerFactory.getLogger(TimerTask.class);

    private String taskId;

    private Callable<T> task;

    private ExecutorService executorService;

    private long triggerTime;

    public TimerTask(String taskId, Callable<T> task, ExecutorService executorService, long triggerTime) {
        this.taskId = taskId;
        this.task = task;
        this.executorService = executorService;
        this.triggerTime = triggerTime;
    }

    public Future<T> call() {
        Future<T> future = executorService.submit(task);
        logger.debug("task {} expected execution time {}ms, actual execution time {}ms.", taskId, triggerTime, System.currentTimeMillis());
        return future;
    }
}
