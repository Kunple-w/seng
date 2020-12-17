package com.github.scheduler.repository;

import com.github.seng.core.job.ShardInfo;
import lombok.Data;

import java.util.Map;

@Data
public class JobDO {
    private String jobId;

    private String jobName;

    private String host;

    private String scheduleType;

    private ShardInfo shardInfo;

    private String timeExpressionType;

    private String timeExpression;

    private String invokeType;

    private String executor;

    private Map<String, Object> executorParams;

    private boolean needCallback;

    private String callbackAddress;

    private Map<String, Object> callbackParams;

    private String overridePolicy;

    private long nextTriggerTime;

    private long lastTriggerTime;

    private Runnable command;
}
