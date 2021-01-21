package com.github.scheduler.schedule.strategy;

import java.util.List;


/**
 * 任务调度策略
 *
 * @author wangyongxu
 * @date 2020/9/14 19:47
 */
public interface JobScheduleStrategy {

    String schedule(String jobId, List<String> address);

}
