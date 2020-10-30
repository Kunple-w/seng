package com.github.seng.core.rpc;

import com.github.seng.core.register.DefaultProvider;
import com.github.seng.core.register.Provider;
import com.github.seng.core.register.RegisterService;
import com.github.seng.core.transport.Server;
import com.github.seng.core.utils.NetUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangyongxu
 */
public class Exporter<T> {
    public static Map<Class<?>, Provider<?>> providerMap = new ConcurrentHashMap<>();
    private String host;
    private int port;
    private Server server;
    private RegisterService registerService;

    public void export(Server server, Object service) {
        server.registerService(service);
    }

//    public void export(Class<T> interfaceClazz, T service, List<URL> registryUrls) {
//        URL url = buildUrl();
//        DefaultProvider provider = new DefaultProvider(url, interfaceClazz, service);
//        providerMap.put(interfaceClazz, provider);
//    }
//
//    public void register(Class<T> interfaceClazz, T service) {
//        registerService.register();
//    }
//
//
//    private String getHostToBind() {
//
//    }
//    private URL buildUrl(Class<T> interfaceClazz, T service) {
//
//        String localHost = NetUtils.getLocalHost();
//        new URL("seng",localHost,)
//
//    }
}
