package com.github.seng.core.register;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * 提供者
 *
 * @author wangyongxu
 */
public interface Provider<T> extends Node {


    /**
     * get this provider interface
     *
     * @return java.lang.Class<T>
     * @author wangyongxu
     */
    Class<T> getInterface();

    /**
     * lookup method
     *
     * @param methodName : method name
     * @param methodDesc : method description
     * @return Java method
     * @author wangyongxu
     */
    @Nullable
    Method lookupMethod(String methodName, String methodDesc);

    /**
     * get impl
     *
     * @return object
     * @author wangyongxu
     */
    T getImpl();
}
