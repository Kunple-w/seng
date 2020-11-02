package com.github.seng.core.rpc.config;

import com.github.seng.core.register.URLConstant;
import com.github.seng.core.rpc.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author wangyongxu
 */
public class ReferenceConfig<T> implements Supplier<T>, LifeCycle {

    private volatile State state = State.READY;

    /**
     * interface class
     */
    private Class<T> interfaceClazz;

    public ReferenceConfig(Class<T> interfaceClazz, RegistryConfig registryConfig) {
        this.interfaceClazz = interfaceClazz;
        this.registryConfigs.add(registryConfig);
    }

    /**
     * registry config list
     */
    protected List<RegistryConfig> registryConfigs = new ArrayList<>();

    protected List<URL> registryUrls = new ArrayList<>();


    @Override
    public T get() {
        if (state == State.READY) {
            init();
        }
        return null;
    }

    private void loadRegistryURL() {
        for (RegistryConfig registryConfig : registryConfigs) {
            URL url = new URL(registryConfig.getProtocol(), registryConfig.getHost(), registryConfig.getPort(), "");
            url.setParam(URLConstant.TIMEOUT_KEY, String.valueOf(registryConfig.getTimeout()));
            registryUrls.add(url);
        }
    }


    @Override
    public boolean isAvailable() {
        return state == State.NORMAL;
    }

    public void init() {
        loadRegistryURL();
        normal();
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
