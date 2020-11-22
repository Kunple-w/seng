package com.github.scheduler.schedule;

import lombok.Data;

import java.util.ArrayList;

/**
 * wheel timer
 * @author qiankewei
 */
@Data
public class WheelTimer {

    /**
     * 底层存1分钟的任务，每格代表1s
     */
    private ArrayList<HashedWheelBucket> buckets = new ArrayList<>(60);

    /**
     * 当前指针指向哪一格
     */
    private int tick;

    /**
     * 当前时间轮是从哪一时刻算起的
     */
    private long startTime;
}
