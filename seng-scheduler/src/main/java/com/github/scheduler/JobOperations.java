package com.github.scheduler;

/**
 * job 操作接口
 *
 * @author wangyongxu
 */
public interface JobOperations {

    /**
     * Trigger job to run at once.
     * 单次触发任务
     *
     * @param jobName : job name
     * @author wangyongxu
     */
    void trigger(String jobName);

    /**
     * 启用任务
     *
     * @param jobName : 任务名称
     * @author wangyongxu
     */
    void enable(String jobName);

    /**
     * disable job<
     *
     * @param jobName : 任务名称
     * @author wangyongxu
     */
    void disable(String jobName);


    /**
     * 停止任务
     *
     * @param jobName : job name
     */
    void shutdown(String jobName);

    /**
     * 移除任务
     *
     * @param jobName : job name
     */
    void remove(String jobName);
}

