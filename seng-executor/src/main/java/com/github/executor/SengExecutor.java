package com.github.executor;

import com.github.seng.core.job.SengContext;

/**
 * 执行器
 *
 * @author wangyongxu
 */
public interface SengExecutor {

    void execute(SengContext sengContext);

}
