package com.github.seng.core.rpc.config;

import com.github.seng.core.register.RegisterService;
import com.github.seng.core.register.RegistryFactory;
import com.github.seng.core.register.URLConstant;
import com.github.seng.core.rpc.URL;
import com.github.seng.core.spi.ExtensionLoader;

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

    protected List<RegisterService> registerServices = new ArrayList<>();

    protected URL expect;

    @Override
    public T get() {
        if (state == State.READY) {
            init();
        }
        for (RegisterService registerService : registerServices) {
            List<URL> lookup = registerService.lookup(expect);
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
        loadRegistryList();
        // TODO: 2020-11-04 06:57:17 export service by wangyongxu
        normal();
    }

    protected void export(URL url) {
        for (RegisterService registerService : registerServices) {
            registerService.register(url);
        }
    }

    protected void loadRegistryList() {
        ExtensionLoader<RegistryFactory> extensionLoader = ExtensionLoader.getExtensionLoader(RegistryFactory.class);
        for (URL registryUrl : registryUrls) {
            registerServices.add(extensionLoader.getExtension(registryUrl.getProtocol()).getRegistry(registryUrl));
        }
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
