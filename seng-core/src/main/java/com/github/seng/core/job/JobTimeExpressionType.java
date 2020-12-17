package com.github.seng.core.job;

/**
 * time expression: cron/fix_rate/fix_delay/initial_delay
 * @author qiankewei
 * @date 2020/11/20 19:28
 * @version v1.0.0
 */
public enum JobTimeExpressionType {
    /**
     * cron expression
     */
    CRON("corn"),

    FIX_RATE("fixrate"),

    FIX_DELAY("fixdelay"),

    INITIAL_DELAY("initialdelay");

    private String timeExpressionType;

    JobTimeExpressionType(String timeExpressionType) {
        this.timeExpressionType = timeExpressionType;
    }
}
