package com.github.seng.core.job;

/**
 * 任务类型
 *
 * @author wangyongxu
 */
public enum JobInvokeType {

    /**
     * java 方法调用
     */
    JAVA_METHOD,

    /**
     * 脚本执行
     */
    SCRIPT,

    /**
     * http请求
     */
    HTTP
}
