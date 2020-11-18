package com.github.scheduler.service;

import com.github.seng.core.job.JobInfo;

import java.util.List;

/**
 * job service
 * @author qiankewei
 */
public class JobService {

    public String addJob(JobInfo jobInfo) {
        return null;
    }

    public boolean deleteJob(List<String> jobIds) {
        return true;
    }

    public boolean updateJob(JobInfo jobInfo) {
        return true;
    }

    public boolean discardJob(List<String> jobIds){
        return true;
    }

    public boolean enableJob(List<String> jobIds) {
        return true;
    }

    public List<JobInfo> queryJobs(List<String> jobIds) {
        return null;
    }


}
