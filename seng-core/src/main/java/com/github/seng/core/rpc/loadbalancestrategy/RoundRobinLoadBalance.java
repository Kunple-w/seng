package com.github.seng.core.rpc.loadbalancestrategy;

import com.github.seng.common.URL;
import com.github.seng.core.rpc.LoadBalance;
import com.github.seng.core.rpc.Reference;

import java.util.List;

/**
 * @author qiankewei
 */
public class RoundRobinLoadBalance<T> implements LoadBalance<T> {
    @Override
    public Reference<T> select(List<Reference<T>> references, URL url) {
        return null;
    }
}
