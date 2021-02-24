package com.github.scheduler.schedule;

import com.github.seng.core.job.JobContext;
import com.github.seng.core.job.JobResult;
import com.github.seng.core.job.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @author wangyongxu
 */
public class TaskRpcDispatcher implements Callable<List<JobResult>> {
    private static final Logger logger = LoggerFactory.getLogger(TaskRpcDispatcher.class);

    private List<TaskInfo> taskInfos;

    public TaskRpcDispatcher(List<TaskInfo> taskInfos) {
        this.taskInfos = taskInfos;
    }

    @Override
    public List<JobResult> call() throws Exception {
        logger.info("task should be dispatched");
        // TODO: 2021-01-21 04:35:31 rpc分发给执行器 by wangyongxu
        if(CollectionUtils.isEmpty(taskInfos)) {
            return null;
        }
        return taskInfos.stream().map(taskInfo -> {
            JobContext jobContext = assembleJobContext(taskInfo);
            JobResult jobResult = taskInfo.getSengExecutor().execute(jobContext);
            return jobResult;
        }).collect(Collectors.toList());
    }

    private JobContext assembleJobContext(TaskInfo taskInfo) {
        return new JobContext();
    }
}
