package com.github.seng.core.rpc;

import com.github.seng.core.transport.Client;
import com.github.seng.core.transport.Invocation;
import com.github.seng.core.transport.Invocations;
import com.github.seng.core.transport.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangyongxu
 */
public class Reference {
    public <T> T refer(Client client, Class<T> serviceClass) {

        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new RemoteInvocationHandler(client));
    }

    private static class RemoteInvocationHandler implements InvocationHandler {
        private Client client;

        public RemoteInvocationHandler(Client client) {
            this.client = client;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Invocation invocation = Invocations.parseInvocation(method, args);
            Request request = new Request(invocation);
            return client.send(request).getBody();
        }
    }

}
