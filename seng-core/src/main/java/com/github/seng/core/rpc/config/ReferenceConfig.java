package com.github.seng.core.rpc.config;

import com.github.seng.common.LifeCycle;
import com.github.seng.common.URL;
import com.github.seng.core.rpc.Exporter;
import com.github.seng.core.rpc.Reference;
import com.github.seng.core.transport.Client;
import com.github.seng.core.transport.EndPointFactory;
import com.github.seng.core.transport.EndPointFactoryImpl;
import com.github.seng.registry.api.RegisterService;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangyongxu
 */
public class ReferenceConfig<T> extends AbstractRegistryHandler implements LifeCycle {

    private volatile State state = State.READY;

    /**
     * interface class
     */
    private Class<T> interfaceClazz;

    private T proxy;

    private EndPointFactory endPointFactory = new EndPointFactoryImpl();


    public ReferenceConfig(Class<T> interfaceClazz, RegistryConfig registryConfig) {
        this.interfaceClazz = interfaceClazz;
        this.registryConfigs.add(registryConfig);
    }

    private Reference<T> reference;
    /**
     * url for lookup interfaceClass
     */
    private URL lookupURL;


    protected void initReference(URL url) {
        Client client = createClient(url);
        reference = new Reference<>(client, interfaceClazz);
    }


    private Client createClient(URL url) {
        return endPointFactory.createClient(new InetSocketAddress(url.getHost(), url.getPort()));
    }


    public T refer() {
        if (state == State.READY) {
            init();
        }
        for (RegisterService registerService : registerServices) {
            List<URL> lookup = registerService.lookup(lookupURL);
            // TODO: 2020-11-06 09:48:58 负载均衡 by wangyongxu
            Iterator<URL> iterator = lookup.iterator();
            if (iterator.hasNext()) {
                URL next = iterator.next();
                initReference(next);
            }

        }
        return reference.refer();
    }


    @Override
    public boolean isAvailable() {
        return state == State.NORMAL;
    }

    public void init() {
        initRegistry();
        lookupURL = new URL(interfaceClazz.getName());
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
