package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;
import com.github.seng.core.transport.Client;
import com.github.seng.core.transport.Request;
import com.github.seng.core.transport.Response;
import com.github.seng.core.transport.Server;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyongxu
 */
public abstract class AbstractProvider<T> implements Provider<T>, Consumer {

    private Client client;

    private Server server;

    private Map<String, Method> methodMap = new HashMap<>();

    @Override
    public Class<T> getInterface() {
        return null;
    }

    @Nullable
    @Override
    public Method lookupMethod(String methodName, String methodDesc) {
        return null;
    }

    @Override
    public T getImpl() {
        return null;
    }

    @Override
    public Response call(Request request) {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public URL getURL() {
        return null;
    }

    @Override
    public void destroy() {
    }
}
