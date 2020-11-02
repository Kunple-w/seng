package com.github.seng.core.rpc;

import com.github.seng.core.register.DefaultProvider;
import com.github.seng.core.register.Provider;
import com.github.seng.core.register.RegisterService;
import com.github.seng.core.register.URLConstant;
import com.github.seng.core.transport.Server;
import com.github.seng.core.utils.NetUtils;
import com.github.seng.core.utils.ReflectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangyongxu
 */
public class Exporter<T> {

    public static Map<Class<?>, Provider<?>> providerMap = new ConcurrentHashMap<>();

    private String protocol;

    private String host;

    private int port;

    private Server server;

    private RegisterService registerService;

    public void export(Server server, Object service) {
        server.registerService(service);
    }

    public void export(Class<T> interfaceClazz, T impl) {
        URL url = buildUrl(interfaceClazz);
        DefaultProvider<T> provider = new DefaultProvider<>(url, interfaceClazz, impl);
        providerMap.put(interfaceClazz, provider);
        registerService.register(url);
    }

    public void unExport(Class<T> interfaceClazz) {
        URL url = buildUrl(interfaceClazz);
        registerService.unregister(url);
    }

    public void register(Class<T> interfaceClazz, T service) {
//        registerService.register();
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
