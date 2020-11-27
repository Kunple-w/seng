package com.github.seng.core.annotation;

import com.github.seng.core.job.JobScheduleType;
import com.github.seng.core.job.OverridePolicy;

import java.lang.annotation.*;

/**
 * task annotation
 *
 * @author wangyongxu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Task {

    String id();

    String name() default "";


    String desc() default "";

    /**
     *cron
     */
    String cron() default "";

    long fixedDelay() default -1;

    long fixedRate() default -1;

    long initialDelay() default 0;

    JobScheduleType scheduleType() default JobScheduleType.STANDALONE;


    /**
     * if job has existed, override policy
     */
    OverridePolicy overridePolicy() default OverridePolicy.IGNORE;


}
