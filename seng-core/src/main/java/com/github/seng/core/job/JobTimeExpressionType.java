package com.github.seng.core.job;

/**
 * time expression: cron/fix_rate/fix_delay
 * @author qiankewei
 * @date 2020/11/20 19:28
 * @version v1.0.0
 */
public enum JobTimeExpressionType {
    /**
     * cron expression
     */
    CRON,

    FIX_RATE,

    FIX_DELAY,

    INITIAL_DELAY;
}
