package com.github.scheduler.schedule.time;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 *
 * @author qiankewei
 */
@Data
public class WheelBucket {
    private List<TimerTask> tasks = new LinkedList<>();

    private ConcurrentHashMap<String, Future> resultMap = new ConcurrentHashMap<>();

    private final Object lock = new Object();

    public void runTask() {
        synchronized (lock) {
            for (TimerTask task : tasks) {
                Future future = task.call();
                resultMap.putIfAbsent(task.getTaskId(), future);
            }
            if (!tasks.isEmpty()) {
                tasks.clear();
            }
        }
    }

    public void removeByTaskId(String taskId) {
        synchronized (lock) {
            if (resultMap.containsKey(taskId)) {
                Future future = resultMap.get(taskId);
                if (!future.isDone() && !future.isCancelled()) {
                    future.cancel(true);
                }
                resultMap.remove(taskId);
                return;
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
            }
        }
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
