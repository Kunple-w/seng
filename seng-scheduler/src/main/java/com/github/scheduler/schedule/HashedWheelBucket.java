package com.github.scheduler.schedule;



import com.github.scheduler.repository.JobDO;
import lombok.Data;

import java.util.List;

@Data
public class HashedWheelBucket {
    private List<JobDO> jobDOs;
}
