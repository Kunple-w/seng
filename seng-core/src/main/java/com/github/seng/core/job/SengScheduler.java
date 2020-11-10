package com.github.seng.core.job;

/**
 * @author wangyongxu
 */
public interface SengScheduler {

    /**
     * 接受任务
     *
     * @param jobInfo : job info
     * @author wangyongxu
     */
    void schedule(JobInfo jobInfo);
}
