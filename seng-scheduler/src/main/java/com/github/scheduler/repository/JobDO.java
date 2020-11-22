package com.github.scheduler.repository;

import lombok.Data;

@Data
public class JobDO {
    private long nextTriggerTime;

    private long lastTriggerTime;
}
