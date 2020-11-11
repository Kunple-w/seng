package com.github.seng.core.rpc.config;

import com.github.seng.common.LifeCycle;
import com.github.seng.common.URL;
import com.github.seng.common.URLConstant;
import com.github.seng.common.spi.ExtensionLoader;
import com.github.seng.core.rpc.LoadBalance;
import com.github.seng.core.rpc.Reference;
import com.github.seng.core.transport.Client;
import com.github.seng.core.transport.EndPointFactory;
import com.github.seng.core.transport.EndPointFactoryImpl;
import com.github.seng.registry.api.RegisterService;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author wangyongxu
 */
public class ReferenceConfig<T> extends AbstractRegistryHandler implements LifeCycle {

    private volatile State state = State.READY;

    /**
     * interface class
     */
    private Class<T> interfaceClazz;

    private EndPointFactory endPointFactory = new EndPointFactoryImpl();

    /**
     * 节点选举
     */
    private String loadBalanceStrategy = URLConstant.LOAD_BALANCE_RANDOM;


    public ReferenceConfig(Class<T> interfaceClazz, RegistryConfig registryConfig) {
        this.interfaceClazz = interfaceClazz;
        this.registryConfigs.add(registryConfig);
    }

    private Map<URL, Reference<T>> referenceMap = new HashMap<>();
    /**
     * url for lookup interfaceClass
     */
    private URL lookupURL;


    protected void initReference(URL url) {
        Client client = createClient(url);
        referenceMap.putIfAbsent(url, new Reference<>(client, interfaceClazz));
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
            Iterator<URL> iterator = lookup.iterator();
            if (iterator.hasNext()) {
                URL next = iterator.next();
                initReference(next);
            }
        }
        Reference<T> selected = select();
        return selected.refer();
    }

    private Reference<T> select() {
        ExtensionLoader<LoadBalance> extensionLoader = ExtensionLoader.getExtensionLoader(LoadBalance.class);
        LoadBalance<T> loadBalance = (LoadBalance<T>) extensionLoader.getExtension(this.loadBalanceStrategy);
        return loadBalance.select(new ArrayList<>(referenceMap.values()), lookupURL);
    }


    @Override
    public boolean isAvailable() {
        return state == State.NORMAL;
    }

    public void init() {
        initRegistry();
        lookupURL = new URL(interfaceClazz.getName());
        lookupURL.setParam(URLConstant.LOAD_BALANCE_KEY, loadBalanceStrategy);
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

    public String getLoadBalanceStrategy() {
        return loadBalanceStrategy;
    }

    public void setLoadBalanceStrategy(String loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }
}
