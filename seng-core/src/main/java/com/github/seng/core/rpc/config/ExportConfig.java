package com.github.seng.core.rpc.config;

import com.github.seng.common.URL;
import com.github.seng.core.rpc.Exporter;
import com.github.seng.core.transport.EndPointFactory;
import com.github.seng.core.transport.EndPointFactoryImpl;
import com.github.seng.core.transport.Server;
import com.github.seng.registry.api.RegisterService;

/**
 * @author wangyongxu
 */
public class ExportConfig<T> extends AbstractRegistryHandler {

    /**
     * interface class
     */
    private Class<T> interfaceClazz;

    private T impl;
    /**
     * provider url
     */
    protected URL providerUrl;

    private Exporter<T> exporter;

    private ServiceConfig serviceConfig;


    private final EndPointFactory endPointFactory = new EndPointFactoryImpl();

    public ExportConfig(Class<T> interfaceClazz, T impl, RegistryConfig registryConfig, ServiceConfig serviceConfig) {
        super(registryConfig);
        this.interfaceClazz = interfaceClazz;
        this.impl = impl;
        this.serviceConfig = serviceConfig;
    }

    public ExportConfig(RegistryConfig registryConfig) {
        super(registryConfig);
    }

    protected void initExporter() {
        exporter = new Exporter<>(interfaceClazz, impl);
        exporter.setProtocol(serviceConfig.getProtocol());
        exporter.setPort(serviceConfig.getPort());
        exporter.setServer(getServer());
        exporter.export();

        providerUrl = exporter.getURL();
    }

    private Server getServer() {
        return endPointFactory.createServer(serviceConfig.getPort());
    }

    public void export() {
        initRegistry();
        initExporter();
        exportToRegistry(providerUrl);
    }

    protected void exportToRegistry(URL url) {
        for (RegisterService registerService : registerServices) {
            registerService.register(url);
        }
    }

}
