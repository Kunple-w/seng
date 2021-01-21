package com.github.seng.core.job;

import lombok.Data;

import java.util.Map;

/**
 * 独立成每一个小部分进行执行
 * @author qiankewei
 */
@Data
public class TaskInfo {
    /**
     * 分发到executor里的任务Id
     */
    private String taskId;

    /**
     * 所属的jobId（用来上报任务状态时用）
     */
    private String jobId;

    /**
     * 根据不同的任务执行类型，选择一个执行器
     */
    private SengExecutor sengExecutor;

    /**
     * 任务中带有的执行器名称或脚本
     */
    private String jobExecutor;

    /**
     * 执行器参数
     */
    private Map<String, Object> executorParams;

    /**
     * 任务结果是否需要回调
     */
    private boolean needCallBack;

    /**
     * 回调地址
     */
    private String callBackAddress;

    /**
     * 回调参数
     */
    private Map<String, Object> callBackParams;
}
