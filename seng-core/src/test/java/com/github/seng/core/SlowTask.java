package com.github.seng.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.*;
import java.util.Map;

/**
 * @author wangyongxu
 */
public class SlowTask {
    private static final Logger logger = LoggerFactory.getLogger(SlowTask.class);

    static class SimpleReduce implements Reduce {
        @Override
        public void reduce(Map<String, String> map) {
        }
    }

    @SengTask(cron = "* * 1 * * *", reduce = SimpleReduce.class)
    void doSomeThing(String a, String b) {
        logger.info("任务执行: {}, {}", a, b);
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface SengTask {
        String cron() default "";
        Class<?> reduce() default Void.class;
    }

    interface Reduce {
        void reduce(Map<String, String> map);
    }
}
