package com.github.scheduler.schedule;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * 随机执行策略
 * @author qiankewei
 * @version v1.0.0
 */
public class RandomExecuteStrategy implements JobScheduleStrategy {
    @Override
    public String schedule(String jobId, List<String> address) {
        return address.get(RandomUtils.nextInt(0, address.size()));
    }
}
