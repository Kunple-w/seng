package com.github.seng.core.job;

/**
 * @author wangyongxu
 */
public interface SengClient {

    /**
     * submit job
     *
     * @param jobInfo : 任务信息
     * @author wangyongxu
     */
    JobResult submit(JobInfo jobInfo);
}
