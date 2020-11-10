package com.github.seng.core.job;

/**
 * @author wangyongxu
 */
public interface SengExecutor {


    /**
     * @param context : 任务上下文
     * @return com.github.seng.core.job.JobResult
     * @author wangyongxu
     */
    JobResult execute(JobContext context);
}
