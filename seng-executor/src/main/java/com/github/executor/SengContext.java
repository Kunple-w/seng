package com.github.executor;

import lombok.Data;

import java.util.Map;

/**
 * 上下午
 *
 * @author wangyongxu
 */
@Data
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
     * 分片总数量
     */
    private int shardTotalCount;

    /**
     * 分片索引
     */
    private int shardIndex;

    private String jobType;

    /**
     * 额外参数
     */
    private Map<String, String> attachment;


    private static final ThreadLocal<SengContext> holder = new ThreadLocal<>();

    public static void setContext(SengContext sengContext) {
        holder.set(sengContext);
    }

    public static SengContext getSengContext() {
        return holder.get();
    }

}
