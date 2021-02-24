package com.github.scheduler.schedule;

import com.github.scheduler.repository.CacheJobRepository;
import com.github.scheduler.repository.JobRepository;
import com.github.seng.core.NodeRegistry;
import com.github.seng.core.job.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DefaultScheduleServiceTest {

    private JobRepository jobRepository = new CacheJobRepository();

    private NodeRegistry nodeRegistry = new NodeRegistry();

    @BeforeEach
    void setup() {
        for(int i = 0; i < 1000 ; i++) {
            JobInfo jobInfo = jobInfo();
            jobInfo.setName("test-job-name-" + i);
            jobRepository.save(jobInfo);
        }
    }

    private JobInfo jobInfo() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(UUID.randomUUID().toString());
        jobInfo.setName("test-job-name-123");
//        jobInfo.setHost();
        jobInfo.setScheduleType(JobScheduleType.STANDALONE);
//        jobInfo.setShardInfo();
        jobInfo.setTimeExpressionType(JobTimeExpressionType.CRON);
        jobInfo.setTimeExpression("*/2 * * * * *");
        jobInfo.setInvokeType(JobInvokeType.JAVA_METHOD);
//        jobInfo.setExecutor();
//        jobInfo.setExecutorParams();
//        jobInfo.setNeedCallback();
//        jobInfo.setCallbackAddress();
//        jobInfo.setCallbackParams();
        jobInfo.setOverridePolicy(OverridePolicy.IGNORE);
        jobInfo.setGroup("order");
        return jobInfo;
    }

    @Test
    void start() throws InterruptedException {
        DefaultScheduleService scheduleService = new DefaultScheduleService(jobRepository, nodeRegistry);
        scheduleService.start();
        TimeUnit.MINUTES.sleep(10);
    }

    @Test
    void stop() {
    }
}