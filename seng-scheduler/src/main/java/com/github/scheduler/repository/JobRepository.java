package com.github.scheduler.repository;

import com.github.seng.core.job.JobInfo;

import java.util.Iterator;

/**
 * job crud
 *
 * @author wangyongxu
 */
public interface JobRepository {


    int save(JobInfo jobInfo);

    JobInfo selectOne(String jobName);

    int delete(String jobName);

    Iterator<JobInfo> iterator(JobInfo condition);

}
