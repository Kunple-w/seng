package com.github.scheduler.schedule.strategy;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 轮询执行策略
 * @author qiankewei
 * @version v1.0.0
 */
public class RoundRobinExecuteStrategy implements JobScheduleStrategy {

    private static ConcurrentHashMap<String, Integer> roundIndexMap = new ConcurrentHashMap<>();

    @Override
    public String schedule(String jobId, List<String> address) {
        Integer index = roundIndexMap.putIfAbsent(jobId, 0);
        if(index == 0) {
            return address.get(0);
        } else if(index >= address.size() - 1) {
            roundIndexMap.put(jobId, 0);
            return address.get(0);
        } else {
            roundIndexMap.put(jobId, index + 1);
            return address.get(index);
        }
    }
}
