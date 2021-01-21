package com.github.scheduler.schedule.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 *
 * @author qiankewei
 */

public class WheelBucket {

    private static final Logger logger = LoggerFactory.getLogger(WheelBucket.class);

    private List<TimerTask> tasks = new LinkedList<>();

    private ConcurrentHashMap<String, Future> resultMap = new ConcurrentHashMap<>();

    private final Object lock = new Object();

    public void runTask() {
        long startTime = System.currentTimeMillis();
        synchronized (lock) {
            for (TimerTask task : tasks) {
                Future future = task.call();
                resultMap.putIfAbsent(task.getTaskId(), future);
            }
            if (!tasks.isEmpty()) {
                tasks.clear();
            }
        }
        long endTime = System.currentTimeMillis();
        logger.debug("执行该格开始时间：" + startTime + ",执行一格花费的时间： " + (endTime - startTime));
    }

    @Override
    public String toString() {
        return tasks.toString();
    }

    public boolean removeByTaskId(String taskId) {
        synchronized (lock) {
            if (resultMap.containsKey(taskId)) {
                Future future = resultMap.get(taskId);
                if (!future.isDone() && !future.isCancelled()) {
                    future.cancel(true);
                }
                resultMap.remove(taskId);
                return true;
            }
            boolean flag = false;
            int index = 0;
            for (int i = 0; i < tasks.size(); i++) {
                if (taskId.equals(tasks.get(i).getTaskId())) {
                    index = i;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                tasks.remove(index);
                return true;
            }
        }
        return false;
    }

    public Future getAndRemoveResult(String taskId) {
        synchronized (lock) {
            if (resultMap.containsKey(taskId)) {
                Future future = resultMap.get(taskId);
                resultMap.remove(taskId);
                return future;
            }
        }
        return null;
    }

    public void addTask(TimerTask timerTask) {
        synchronized (lock) {
            tasks.add(timerTask);
        }
    }

}
