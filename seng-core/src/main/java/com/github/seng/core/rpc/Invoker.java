package com.github.seng.core.rpc;

import com.github.seng.common.Node;
import com.github.seng.core.register.Callable;

/**
 * @author wangyongxu
 */
public interface Invoker<T> extends Node, Callable {
    /**
     * get this provider interface
     *
     * @return java.lang.Class<T>
     * @author wangyongxu
     */
    Class<T> getInterface();
}
