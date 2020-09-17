package com.github.seng.core.job;

/**
 * 调度类型
 *
 * @author wangyongxu
 */
public enum ScheduleType {

    /**
     * 广播
     */
    BROADCAST,

    /**
     * 随机执行一次
     */
    RANDOM,

    /**
     * 轮训
     */
    ROUND_ROBIN,

    /**
     * 提交者执行
     */
    SUBMITTER,

    /**
     * 最少执行次数
     */
    LEAST_EXECUTE,

    /**
     * 最短执行时间
     */
    LEAST_RESPONSE,

    /**
     * 最佳选择
     */
    BEST_CHOICE


}
