package com.github.scheduler.repository;

import lombok.Data;

@Data
public class JobDO {
    private String jobId;

    private long nextTriggerTime;

    private long lastTriggerTime;

    private Runnable command;
}
