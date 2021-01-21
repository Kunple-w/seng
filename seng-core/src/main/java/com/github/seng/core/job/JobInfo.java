package com.github.seng.core.job;

import lombok.Data;

import java.util.Map;

/**
 * @author wangyongxu
 */
@Data
public class JobInfo {

    /**
     * ---------------------------- 任务自身属性 ----------------------------
     */

    /**
     * 任务id
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 业务组
     */
    private String group;

    /**
     * 任务提交者信息
     */
    private String host;

    /**
     * 任务调度类型
     */
    private JobScheduleType scheduleType;

    /**
     * 分片信息
     */
    private ShardInfo shardInfo;

    /**
     * ------------------------------- 定时信息 -----------------------------start
     */
    private JobTimeExpressionType timeExpressionType;

    private String timeExpression;

    /**
     * ------------------------------- 定时信息 -----------------------------end
     */

    /**
     * ------------------------------- 执行器信息 ----------------------------
     */

    /**
     * 执行器类型
     */
    private JobInvokeType invokeType;

    /**
     * 执行器名称，method类型即类名，script即脚本，http即请求方法+路径
     */
    private String executor;

    /**
     * 执行器参数
     */
    private Map<String, Object> executorParams;

    /**
     * 任务结果是否需要回调
     */
    private boolean needCallback;

    /**
     * 回调地址
     */
    private String callbackAddress;

    /**
     * 回调参数
     */
    private Map<String, Object> callbackParams;


    OverridePolicy overridePolicy;

}
