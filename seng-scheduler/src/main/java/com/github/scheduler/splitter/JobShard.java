package com.github.scheduler.splitter;

import com.github.seng.core.Node;
import com.github.seng.core.job.JobInfo;
import com.github.seng.core.job.TaskInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author wangyongxu
 */
public class JobShard implements JobSplitter {
    @Override
    public List<TaskInfo> split(JobInfo jobInfo, List<Node> executorNodes) {
        // TODO: 2021-01-22 05:18:10 分片 by wangyongxu

        return Collections.emptyList();
    }
}
