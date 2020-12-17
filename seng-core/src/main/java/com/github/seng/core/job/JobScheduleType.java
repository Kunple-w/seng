package com.github.seng.core.job;

/**
 * @author wangyongxu
 */
public enum JobScheduleType {
    /**
     * 广播
     */
    BROADCAST("broadcast"),
    /**
     * 单机
     */
    STANDALONE("standalone"),
    /**
     * map
     */
    MAP("map"),
    /**
     * map_reduce
     */
    MAP_REDUCE("mapreduce");

    private String scheduleType;

    JobScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

}
