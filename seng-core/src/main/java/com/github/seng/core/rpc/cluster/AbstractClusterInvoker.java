package com.github.seng.core.rpc.cluster;

import com.github.seng.common.URL;
import com.github.seng.common.URLConstant;
import com.github.seng.common.spi.ExtensionLoader;
import com.github.seng.core.rpc.Invoker;
import com.github.seng.core.rpc.LoadBalance;
import com.github.seng.core.rpc.RemoteInvoker;
import com.github.seng.core.transport.ApiResult;
import com.github.seng.core.transport.Invocation;
import com.github.seng.registry.api.RegisterService;
import com.github.seng.registry.api.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyongxu
 */
public abstract class AbstractClusterInvoker<T> implements ClusterInvoker<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * registry url, eg zookeeper://127.0.0.1:2181
     */
    protected URL registryUrl;

    protected Class<T> interfaceClass;

    protected RegisterService registerService;


    @Override
    public URL getRegistryURL() {
        return registryUrl;
    }

    protected RegisterService loadRegistry() {
        ExtensionLoader<RegistryFactory> extensionLoader = ExtensionLoader.getExtensionLoader(RegistryFactory.class);
        return extensionLoader.getExtension(registryUrl.getProtocol()).getRegistry(registryUrl);
    }

    @Override
    public synchronized RegisterService getRegistry() {
        if (registerService == null) {
            registerService = loadRegistry();
        }
        return registerService;
    }

    @Override
    public Class<T> getInterface() {
        return interfaceClass;
    }

    @Override
    public ApiResult call(Invocation invocation) {
        URL lookupURL = new URL(invocation.getServiceName());
        List<URL> urlList = registerService.lookup(lookupURL);
        List<Invoker<T>> invokers = providerList(urlList);
        LoadBalance loadBalance = initLoadBalance(invocation, invokers);
        return call(invocation, invokers, loadBalance);
    }

    protected List<Invoker<T>> providerList(List<URL> urlList) {
        List<Invoker<T>> invokers = new ArrayList<>();
        for (URL url : urlList) {
            RemoteInvoker<T> remoteInvoker = new RemoteInvoker<>(url, interfaceClass);
            invokers.add(remoteInvoker);
        }
        return invokers;
    }

    protected LoadBalance initLoadBalance(Invocation invocation, List<Invoker<T>> invokers) {
        ExtensionLoader<LoadBalance> extensionLoader = ExtensionLoader.getExtensionLoader(LoadBalance.class);
        return extensionLoader.getExtension(invokers.get(0).getURL().getParam(URLConstant.LOAD_BALANCE_KEY, URLConstant.LOAD_BALANCE_RANDOM));
    }

    protected Invoker<T> select(LoadBalance<T> loadBalance, List<Invoker<T>> invokers, Invocation invocation) {
        if (invokers.isEmpty()) {
            return null;
        }
        return loadBalance.selectInvoker(invokers, getURL(), invocation);
    }

    public abstract ApiResult call(Invocation invocation, List<Invoker<T>> invokers, LoadBalance<T> loadBalance);

    @Override
    public URL getURL() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return registerService.isAvailable();
    }

    @Override
    public void init() {
        registerService.init();
    }

    @Override
    public void destroy() {
        registerService.destroy();
    }
}