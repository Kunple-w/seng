package com.github.seng.core.rpc;

import com.github.seng.core.register.*;
import com.github.seng.core.transport.Server;
import com.github.seng.core.utils.NetUtils;
import com.github.seng.core.utils.ReflectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangyongxu
 */
public class Exporter<T>  extends AbstractNode {

    public Map<Class<T>, Provider<T>> providerMap = new ConcurrentHashMap<>();

    private String protocol;

    private String host;

    private int port;

    private Server server;

    public Exporter(URL url) {
        super(url);
    }


    public void export(Class<T> interfaceClazz, T impl) {
        URL url = buildUrl(interfaceClazz);
        DefaultProvider<T> provider = new DefaultProvider<>(url, interfaceClazz, impl);
        providerMap.put(interfaceClazz, provider);
        server.registerProvider(provider);
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
        return url;
    }
}
