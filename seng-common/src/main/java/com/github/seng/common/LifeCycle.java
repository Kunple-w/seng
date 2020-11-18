package com.github.seng.common;

/**
 * life cycle
 *
 * @author wangyongxu
 */
public interface LifeCycle {

    boolean isAvailable();

    void init();

    void destroy();


}
