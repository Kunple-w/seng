package com.github.seng.core.rpc.cluster;

import com.github.seng.core.register.Provider;
import com.github.seng.core.transport.Invocation;

import java.util.List;

/**
 * @author wangyongxu
 */
public interface Registry<T> {

    List<Provider<T>> getList(Invocation invocation);
}
