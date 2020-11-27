package com.github.seng.core.job;

/**
 * job override policy
 *
 * @author wangyongxu
 */
public enum OverridePolicy {

    /**
     * ignore if job has existed
     */
    IGNORE,

    /**
     * update job
     */
    UPDATE,


    /**
     * force new job
     * Note: new job id not expected;
     */
    FORCE_NEW,

}
