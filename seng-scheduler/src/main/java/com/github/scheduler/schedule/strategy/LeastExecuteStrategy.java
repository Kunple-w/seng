package com.github.scheduler.schedule.strategy;

import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最少执行次数策略
 * @author qiankewei
 * @version v1.0.0
 */
public class LeastExecuteStrategy implements JobScheduleStrategy {

    private static ConcurrentHashMap<String, HashMap<String, Integer>> frequencyMap = new ConcurrentHashMap<>();

    /**
     * the interval of refreshing
     */
    private static long REFRESH_INTERVAL_TIME = 3 * 24 * 60 * 60 * 1000;

    /**
     * last time of refreshing
     */
    private static long LAST_REFRESH_TIME = System.currentTimeMillis();

    @Override
    public String schedule(String jobId, List<String> address) {
        String result = address.get(0);
        int size = address.size();
        HashMap<String, Integer> map = frequencyMap.get(jobId);
        if (map == null) {
            int randomIndex = RandomUtils.nextInt(0, size);
            map = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                if (i == randomIndex) {
                    map.put(address.get(i), 1);
                } else {
                    map.put(address.get(i), 0);
                }
            }
            result = address.get(randomIndex);
        } else {
            int minValue = Integer.MAX_VALUE;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() < minValue) {
                    minValue = entry.getValue();
                    result = entry.getKey();
                }
            }
            map.put(result, minValue + 1);
        }
        if (System.currentTimeMillis() > LAST_REFRESH_TIME + REFRESH_INTERVAL_TIME) {
            refresh();
        }
        return result;

    }


    private void refresh() {
        frequencyMap.clear();
        LAST_REFRESH_TIME = System.currentTimeMillis();
    }
}
