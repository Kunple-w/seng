package com.github.executor;

import com.github.seng.core.job.JobInvokeType;

/**
 * @author wangyongxu
 */
public class SengExecutorFactory {

    public static SengExecutor getExecutor(JobInvokeType jobInvokeType) {
        switch (jobInvokeType) {
            case JAVA_METHOD:
                return new JavaMethodExecutor();
            case SCRIPT:
                return new ScriptExecutor();
            case HTTP:
                return new HttpExecutor();
            default:
                throw new IllegalStateException("Unexpected value: " + jobInvokeType);
        }
    }
}
