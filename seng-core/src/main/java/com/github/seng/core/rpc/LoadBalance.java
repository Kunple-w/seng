package com.github.seng.core.rpc;

import com.github.seng.common.URL;
import com.github.seng.common.spi.SPI;
import com.github.seng.core.transport.Invocation;

import java.util.List;

/**
 * load balance
 *
 * @author qiankewei
 */

@SPI
public interface LoadBalance<T> {

    Reference<T> select(List<Reference<T>> references, URL url);

    default Invoker<T> selectInvoker(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        return invokers.get(0);
    }

}
