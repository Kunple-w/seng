package com.github.seng.core.rpc.cluster;

import com.github.seng.common.URL;
import com.github.seng.core.rpc.Invoker;
import com.github.seng.core.rpc.LoadBalance;
import com.github.seng.core.transport.ApiResult;
import com.github.seng.core.transport.Invocation;

import java.util.List;

/**
 * @author wangyongxu
 */
public class FailFastClusterInvoker<T> extends AbstractClusterInvoker<T> {
    public FailFastClusterInvoker(URL registryUrl, Class<T> interfaceClass) {
        this.registryUrl = registryUrl;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public ApiResult call(Invocation invocation, List<Invoker<T>> invokers, LoadBalance<T> loadBalance) {
        Invoker invoker = select(loadBalance, invokers, invocation);
        return invoker.call(invocation);
    }
}
