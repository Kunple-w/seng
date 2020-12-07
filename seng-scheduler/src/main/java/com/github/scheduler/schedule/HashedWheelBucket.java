package com.github.scheduler.schedule;


import com.github.scheduler.repository.JobDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HashedWheelBucket {
    private List<JobDO> jobDOs = new ArrayList<>();

    public JobDO ifContains(String jobId) {
        if (jobDOs.isEmpty()) {
            return null;
        }
        synchronized (this) {
            List<JobDO> list = jobDOs.stream().filter(jobDO -> jobId.equals(jobDO.getJobId())).collect(Collectors.toList());
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }
}
