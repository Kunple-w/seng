package com.github.seng.core.rpc.config;

import com.github.seng.common.LifeCycle;
import com.github.seng.common.URL;
import com.github.seng.core.rpc.Invoker;
import com.github.seng.core.rpc.JdkProxyFactory;
import com.github.seng.core.rpc.cluster.FailFastClusterInvoker;

/**
 * @author wangyongxu
 */
public class ReferenceConfig<T> implements LifeCycle {

    private volatile State state = State.READY;

    /**
     * interface class
     */
    private  Class<T> interfaceClazz;

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
        return state == State.NORMAL;
    }

    public void init() {
        initRegistryUrl();
        initRef();
        normal();
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

    public void normal() {
        state = State.NORMAL;
    }

    @Override
    public void destroy() {
        state = State.FINISH;
    }

    @Override
    public State getState() {
        return state;
    }

}
