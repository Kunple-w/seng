package com.github.seng.core.rpc.config;

import com.github.seng.common.LifeCycle;
import com.github.seng.common.URL;
import com.github.seng.core.rpc.Invoker;
import com.github.seng.core.rpc.JdkProxyFactory;
import com.github.seng.core.rpc.cluster.FailFastClusterInvoker;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wangyongxu
 */
public class ReferenceConfig<T> implements LifeCycle {

    private AtomicBoolean available = new AtomicBoolean(false);


    /**
     * interface class
     */
    private Class<T> interfaceClazz;

    private T ref;

    private final RegistryConfig registryConfig;


    private URL registryURL;

    private Invoker<T> invoker;

    private final JdkProxyFactory proxyFactory = new JdkProxyFactory();

    public ReferenceConfig(Class<T> interfaceClazz, RegistryConfig registryConfig) {
        this.interfaceClazz = interfaceClazz;
        this.registryConfig = registryConfig;
    }


    public synchronized T refer() {
        if (ref == null) {
            init();
        }
        return ref;
    }


    @Override
    public boolean isAvailable() {
        return available.get();
    }

    public void init() {
        initRegistryUrl();
        initRef();
        available.set(true);
    }

    protected void initRegistryUrl() {
        registryURL = registryConfig.getUrl();
    }

    protected void initRef() {
        if (invoker == null) {
            invoker = new FailFastClusterInvoker<>(registryURL, interfaceClazz);
            invoker.init();
        }
        ref = proxyFactory.getProxy(invoker, new Class[]{interfaceClazz});
    }


    @Override
    public void destroy() {
        available.set(false);
    }

}
