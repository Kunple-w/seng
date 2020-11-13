package com.github.seng.core.rpc.cluster;

import com.github.seng.common.URL;
import com.github.seng.core.rpc.Invoker;
import com.github.seng.registry.api.RegisterService;

/**
 * @author wangyongxu
 */
public interface ClusterInvoker<T> extends Invoker<T> {


    URL getRegistryURL();

    RegisterService getRegistry();


}

