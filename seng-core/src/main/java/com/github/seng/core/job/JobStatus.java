package com.github.seng.core.job;

/**
 * job状态
 *
 * @author wangyongxu
 */
public enum JobStatus {

    /**
     * job开始
     */
    START,

    /**
     * 暂停, 只有处于WAITING的job才可以暂停
     */
    PAUSE,

    /**
     * 运行中
     */
    RUNNING,

    /**
     * 等待调度
     */
    WAITING,

    /**
     * 中断
     */
    INTERRUPT,

    /**
     * 执行结束
     */
    TERMINAL,

    /**
     * 执行失败，可能抛出了异常
     */
    FAILED

}
