package com.github.executor;

import java.util.Map;

/**
 * 上下午
 *
 * @author wangyongxu
 */
public class SengContext {
    /**
     * 作业id, 一个作业可以拆分为多个任务
     */
    private String jobId;

    /**
     * 任务id, 一个作业可以拆分为多个任务
     */
    private String taskId;
    /**
     * 分片数量
     */
    private int shardCount;

    /**
     * 额外参数
     */
    private Map<String, String> attachment;

}
