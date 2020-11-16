package com.github.seng.core.rpc;

/**
 * @author wangyongxu
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces);
}
