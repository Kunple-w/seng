package com.github.seng.core.job;

/**
 * @author wangyongxu
 */
public interface SengServer {

    /**
     * 接受任务
     *
     * @param jobInfo : job info
     * @author wangyongxu
     */
    void receiveJob(JobInfo jobInfo);
}
