package com.github.seng.registry.consul;

import com.github.seng.core.register.EventListener;
import com.github.seng.core.register.RegisterService;
import com.github.seng.core.rpc.URL;

import java.util.List;

/**
 * @author wangyongxu
 */
public class ConsulRegistry implements RegisterService {
    @Override
    public void register(URL url) {
    }

    @Override
    public void unregister(URL url) {

    }

    @Override
    public List<URL> lookup(URL url) {
        return null;
    }

    @Override
    public void subscribe(URL url, EventListener eventListener) {

    }

    @Override
    public void unsubscribe(URL url, EventListener eventListener) {

    }
}
