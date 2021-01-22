package com.github.scheduler.splitter;

import com.github.seng.core.Node;
import com.github.seng.core.job.JobInfo;
import com.github.seng.core.job.TaskInfo;

import java.util.List;

/**
 * @author wangyongxu
 */
public interface JobSplitter {

    List<TaskInfo> split(JobInfo jobInfo, List<Node> executorNodes);
}
