package com.github.scheduler.repository;

import com.github.seng.core.job.JobInfo;
import com.github.seng.core.job.OverridePolicy;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimaps;

import java.util.*;

/**
 * @author wangyongxu
 */
public class CacheJobRepository implements JobRepository {
    private final Map<String, JobInfo> cache = new HashMap<>();

    @Override
    public int save(JobInfo jobInfo) {
        OverridePolicy overridePolicy = jobInfo.getOverridePolicy();
        switch (overridePolicy) {
            case IGNORE: {
                if (cache.containsKey(jobInfo.getName())) {
                    return 0;
                } else {
                    cache.put(jobInfo.getName(), jobInfo);
                    return 1;
                }
            }

            case UPDATE: {
                if (cache.containsKey(jobInfo.getName())) {
                    cache.put(jobInfo.getName(), jobInfo);
                    return 1;
                } else {
                    return 0;
                }
            }
            case FORCE_NEW: {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public JobInfo selectOne(String jobName) {
        return cache.get(jobName);
    }

    @Override
    public int delete(String jobName) {
        cache.remove(jobName);
        return 0;
    }

    @Override
    public Iterator<JobInfo> iterator(JobInfo condition) {
        return cache.values()
                .stream()
                .filter(jobInfo -> jobInfo.getName().contains(condition.getName()))
                .iterator();

    }

    @Override
    public Iterator<JobInfo> iterator() {
        return cache.values().iterator();
    }

    @Override
    public Map<String, Collection<JobInfo>> getJobs() {
        ArrayListMultimap<String, JobInfo> multimap = ArrayListMultimap.create();
        for (Map.Entry<String, JobInfo> entry : cache.entrySet()) {
            multimap.put(entry.getValue().getGroup(), entry.getValue());
        }
        return multimap.asMap();
    }
}
