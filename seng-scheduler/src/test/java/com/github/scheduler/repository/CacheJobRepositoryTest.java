package com.github.scheduler.repository;

import com.github.seng.core.job.*;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CacheJobRepositoryTest {
    private JobRepository jobRepository = new CacheJobRepository();

    @BeforeEach
    void setup() {
        jobRepository.save(jobInfo());
    }

    @Test
    void save() {

        int count = jobRepository.save(jobInfo());
        assertEquals(0, count);
    }

    private JobInfo jobInfo() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(UUID.randomUUID().toString());
        jobInfo.setName("test-job-name-123");
//        jobInfo.setHost();
        jobInfo.setScheduleType(JobScheduleType.STANDALONE);
//        jobInfo.setShardInfo();
        jobInfo.setTimeExpressionType(JobTimeExpressionType.CRON);
        jobInfo.setTimeExpression("0 0 */12 * * *");
        jobInfo.setInvokeType(JobInvokeType.JAVA_METHOD);
//        jobInfo.setExecutor();
//        jobInfo.setExecutorParams();
//        jobInfo.setNeedCallback();
//        jobInfo.setCallbackAddress();
//        jobInfo.setCallbackParams();
        jobInfo.setOverridePolicy(OverridePolicy.IGNORE);
        return jobInfo;
    }

    @Test
    void selectOne() {
        JobInfo jobInfo = jobRepository.selectOne("test-job-name-123");
        assertNotNull(jobInfo);
    }

    @Test
    void delete() {
        jobRepository.delete("test-job-name-123");
        JobInfo jobInfo = jobRepository.selectOne("test-job-name-123");
        assertNull(jobInfo);
    }

    @Test
    void iterator() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setName("test-job");
        Iterator<JobInfo> iterator = jobRepository.iterator(jobInfo);
        ArrayList<JobInfo> jobInfos = Lists.newArrayList(iterator);
        assertEquals(1, jobInfos.size());
    }
}