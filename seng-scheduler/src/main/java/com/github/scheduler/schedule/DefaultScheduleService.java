package com.github.scheduler.schedule;

import com.github.scheduler.repository.JobRepository;
import com.github.scheduler.schedule.time.TimerTask;
import com.github.scheduler.schedule.time.WheelTimer;
import com.github.seng.core.job.JobInfo;
import com.github.seng.core.job.JobResult;
import com.github.seng.core.threadpool.SengThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @author wangyongxu
 */
public class DefaultScheduleService implements ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultScheduleService.class);
    private JobRepository jobRepository;
    private Map<String, WheelTimer> map = new HashMap<>();

    public DefaultScheduleService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void start() {
        logger.info("seng scheduler start...");
        Map<String, Collection<JobInfo>> jobs = jobRepository.getJobs();
        for (Map.Entry<String, Collection<JobInfo>> entry : jobs.entrySet()) {
            if (map.containsKey(entry.getKey())) {
                map.get(entry.getKey()).loadTasks(entry.getValue().stream().map(this::timerTask).collect(Collectors.toList()));
            } else {
                WheelTimer wheelTimer = new WheelTimer();
                wheelTimer.start();
                wheelTimer.loadTasks(entry.getValue().stream().map(this::timerTask).collect(Collectors.toList()));
                map.put(entry.getKey(), wheelTimer);
            }
//            map.merge(entry.getKey(), new WheelTimer(), (old, val) -> {
//                old.loadTasks(entry.getValue().stream().map(this::timerTask).collect(Collectors.toList()));
//                return old;
//            });
        }
//        startAllWheelTimer();
        printStartSummary();
        logger.info("seng scheduler start success");
    }

    protected void startAllWheelTimer() {
        map.values().forEach(WheelTimer::start);
    }

    private void printStartSummary() {
        // TODO: 2021-01-21 04:43:59 启动完成后打印信息 by wangyongxu
    }

    @Override
    public void stop() {
        logger.info("seng scheduler stop...");
        for (Map.Entry<String, WheelTimer> entry : map.entrySet()) {
            entry.getValue().stop();
        }
        logger.info("seng scheduler stop success.");
    }

    private TimerTask<?> timerTask(JobInfo jobInfo) {
        Callable<JobResult> callable = new TaskRpcDispatcher();

        Date date = new CronTrigger(jobInfo.getTimeExpression()).nextExecutionTime(new SimpleTriggerContext());

        return new TimerTask<>(jobInfo.getId(), callable, SengThreadPoolFactory.defaultFixedThreadPool(jobInfo.getGroup(), true), date.getTime());
    }

    private static class TaskRpcDispatcher implements Callable<JobResult> {
        private static final Logger logger = LoggerFactory.getLogger(TaskRpcDispatcher.class);

        @Override
        public JobResult call() throws Exception {
            logger.info("task should be dispatched");
            // TODO: 2021-01-21 04:35:31 rpc分发给执行器 by wangyongxu
            return new JobResult();
        }
    }


}
