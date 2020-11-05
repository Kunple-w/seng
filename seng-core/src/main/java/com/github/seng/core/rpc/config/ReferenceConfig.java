package com.github.seng.core.rpc.config;

import com.github.seng.common.LifeCycle;
import com.github.seng.common.URLConstant;
import com.github.seng.common.URL;
import com.github.seng.common.spi.ExtensionLoader;
import com.github.seng.core.rpc.Exporter;
import com.github.seng.core.rpc.Reference;
import com.github.seng.core.transport.Client;
import com.github.seng.core.transport.EndPointFactory;
import com.github.seng.core.transport.EndPointFactoryImpl;
import com.github.seng.core.transport.Server;
import com.github.seng.registry.api.RegisterService;
import com.github.seng.registry.api.RegistryFactory;

import java.net.InetSocketAddress;
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

    private T impl;

    private EndPointFactory endPointFactory = new EndPointFactoryImpl();

    private ServiceConfig serviceConfig;


    public ReferenceConfig(Class<T> interfaceClazz, RegistryConfig registryConfig, ServiceConfig serviceConfig) {
        this.interfaceClazz = interfaceClazz;
        this.registryConfigs.add(registryConfig);
        this.serviceConfig = serviceConfig;
    }

    /**
     * registry config list
     */
    protected List<RegistryConfig> registryConfigs = new ArrayList<>();

    protected List<URL> registryUrls = new ArrayList<>();

    protected List<RegisterService> registerServices = new ArrayList<>();

    protected URL expect;

    private Exporter<T> exporter;

    private Reference<T> reference;

    protected void initExport() {
        exporter = new Exporter<>(interfaceClazz, impl);
        exporter.setProtocol("seng");
        exporter.setServer(getServer());
        exporter.export();
        Client client = getClient();
        reference = new Reference<>(client, interfaceClazz);
        expect = exporter.getURL();
    }

    private Server getServer() {
        return endPointFactory.createServer(serviceConfig.getPort());
    }

    private Client getClient() {
        return endPointFactory.createClient(new InetSocketAddress(serviceConfig.getHost(), serviceConfig.getPort()));
    }

    @Override
    public T get() {
        if (state == State.READY) {
            init();
        }
        for (RegisterService registerService : registerServices) {
            List<URL> lookup = registerService.lookup(expect);
        }
        return reference.refer();
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
        initExport();
        export(expect);
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
