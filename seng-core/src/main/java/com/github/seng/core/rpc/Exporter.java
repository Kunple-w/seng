package com.github.seng.core.rpc;

import com.github.seng.common.URL;
import com.github.seng.common.AbstractNode;
import com.github.seng.core.register.DefaultProvider;
import com.github.seng.core.register.Provider;
import com.github.seng.common.URLConstant;
import com.github.seng.core.transport.Server;
import com.github.seng.common.utils.NetUtils;
import com.github.seng.common.utils.ReflectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * export service to server(remote)
 *
 * @author wangyongxu
 */
public class Exporter<T> extends AbstractNode {

    public final Map<Class<T>, Provider<T>> providerMap = new ConcurrentHashMap<>();

    private String protocol;

    private int port;

    private Server server;

    protected Class<T> interfaceClazz;

    protected T impl;

    public Exporter(Class<T> interfaceClazz, T impl) {
        this.interfaceClazz = interfaceClazz;
        this.impl = impl;
    }

    public void export() {
        url = buildUrl(interfaceClazz);
        DefaultProvider<T> provider = new DefaultProvider<>(url, interfaceClazz, impl);
        providerMap.put(interfaceClazz, provider);
        server.registerProvider(provider);
    }

    @Override
    public void init() {
        super.init();
    }

    public void unExport(Class<T> interfaceClazz) {
        Provider<T> provider = providerMap.get(interfaceClazz);
        if (provider != null) {
            server.unregisterProvider(provider);
        }
    }


    private String getHostToBind() {
        return NetUtils.getLocalHost();
    }

    private URL buildUrl(Class<T> interfaceClazz) {
        String localHost = getHostToBind();
        String className = ReflectUtils.getClassName(interfaceClazz);
        String methodNames = ReflectUtils.getClassMethodNames(interfaceClazz);
        URL url = new URL(protocol, localHost, port, className);
        url.getParameters().put(URLConstant.METHOD_KEY, methodNames);
        url.setPath(interfaceClazz.getName());
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
