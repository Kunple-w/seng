package com.github.seng.core.job;

import java.util.Map;

/**
 * @author wangyongxu
 */
public class JobInfo {

    /**
     * 任务id
     */
    private String id;

    /**
     * 分片信息
     */
    private ShardInfo shardInfo;

    /**
     * ------------------------------- 定时信息 -----------------------------start
     */
    private String cron;

    private long fixedDelay;

    private long fixedRate;

    private long initialDelay;

    /**
     * ------------------------------- 定时信息 -----------------------------end
     */

    private Map<Object, Object> attachment;


    private JobInvokeType invokeType;

    private JobScheduleType scheduleType;


}
