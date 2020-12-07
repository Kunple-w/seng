package com.github.scheduler.repository;

import com.github.scheduler.schedule.HashedWheelBucket;
import lombok.Data;

@Data
public class JobDO {
    private String jobId;

    private long nextTriggerTime;

    private long lastTriggerTime;

}
