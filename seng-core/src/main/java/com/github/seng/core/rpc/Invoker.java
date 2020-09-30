package com.github.seng.core.rpc;

import com.github.seng.core.exception.RpcException;
import com.github.seng.core.transport.Invocation;

/**
 * @author wangyongxu
 */
public interface Invoker<T> {

    Class<T> getInterface();

    RpcResult invoke(Invocation invocation) throws RpcException;
}
