package com.github.seng.core.job;

import java.util.List;

/**
 * schedule policy
 *
 * @author wangyongxu
 */
public interface SchedulePolicy {

    JobResult schedule(List<SengExecutor> sengExecutorList, Object scheduleContext);
}
