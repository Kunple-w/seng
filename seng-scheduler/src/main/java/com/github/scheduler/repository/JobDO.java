package com.github.scheduler.repository;

import com.github.scheduler.schedule.HashedWheelBucket;
import lombok.Data;

@Data
public class JobDO {
    private long nextTriggerTime;

    private long lastTriggerTime;

    private HashedWheelBucket hashedWheelBucket;
}
