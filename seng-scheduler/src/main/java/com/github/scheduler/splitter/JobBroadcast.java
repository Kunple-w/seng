package com.github.scheduler.splitter;

import com.github.seng.core.Node;
import com.github.seng.core.job.JobInfo;
import com.github.seng.core.job.TaskInfo;

import java.util.List;

/**
 * @author wangyongxu
 */
public class JobBroadcast implements JobSplitter {
    @Override
    public List<TaskInfo> split(JobInfo jobInfo, List<Node> executorNodes) {
        return null;
    }
}
