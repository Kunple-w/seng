package com.github.scheduler.schedule;

import com.github.seng.core.job.JobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author wangyongxu
 */
public class TaskRpcDispatcher implements Callable<JobResult> {
    private static final Logger logger = LoggerFactory.getLogger(TaskRpcDispatcher.class);

    @Override
    public JobResult call() throws Exception {
        logger.info("task should be dispatched");
        // TODO: 2021-01-21 04:35:31 rpc分发给执行器 by wangyongxu
        return new JobResult();
    }
}
