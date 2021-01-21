package com.github.scheduler.schedule;

import com.github.seng.core.job.JobInfo;
import com.github.seng.core.job.JobScheduleType;
import com.github.seng.core.job.TaskInfo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * 放在时间轮中间的任务，以job为维度，根据job中的调度类型，组成taskInfo，可能是多个taskInfo，ScheduleService进行调度
 * @author qiankewei
 */
public class ScheduleTask implements Callable {
    private JobInfo jobInfo;
    private List<TaskInfo> taskInfos;
    private ScheduleService1 scheduleService;

    public ScheduleTask(JobInfo jobInfo, ScheduleService1 scheduleService) {
        this.jobInfo = jobInfo;
        this.scheduleService = scheduleService;
    }

    @Override
    public Object call() throws Exception {
        JobScheduleType scheduleType = jobInfo.getScheduleType();
        switch (scheduleType) {
            case BROADCAST:
                //获取所有的执行器列表
                break;
            case MAP:
                //静态分片
                break;
            case MAP_REDUCE:
                break;
            case STANDALONE:
                TaskInfo taskInfo = generateTaskInfo();
                taskInfo.setTaskId(generateTaskId(1));
                //根据rpc获取任务执行器
                //taskInfo.setSengExecutor();
                taskInfos = Arrays.asList(taskInfo);
                break;
            default:
                taskInfos = null;

        }
        if(taskInfos != null) {
            return taskInfos.stream().map(taskInfo -> scheduleService.schedule(taskInfo)).collect(Collectors.toList());
        }
        return null;
    }


    private TaskInfo generateTaskInfo() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setJobId(jobInfo.getId());
        taskInfo.setJobExecutor(jobInfo.getExecutor());
        taskInfo.setExecutorParams(jobInfo.getExecutorParams());
        taskInfo.setNeedCallBack(jobInfo.isNeedCallback());
        taskInfo.setCallBackAddress(jobInfo.getCallbackAddress());
        taskInfo.setCallBackParams(jobInfo.getCallbackParams());
        return taskInfo;
    }


    //生成任务ID规则，任务类型+任务id+当前时间后6位+批量任务标号
    private String generateTaskId(int num) {
        return jobInfo.getScheduleType().value() + "_" + jobInfo.getId() + "_" + String.valueOf(System.currentTimeMillis()).substring(8) + "_" + num;
    }
}
