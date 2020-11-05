package com.github.seng.core.rpc;

import com.github.seng.core.transport.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangyongxu
 */
public class Reference {

    @SuppressWarnings("unchecked")
    public <T> T refer(Client client, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new RemoteInvocationHandler(client));
    }

    private static class RemoteInvocationHandler implements InvocationHandler {

        private final Client client;

        public RemoteInvocationHandler(Client client) {
            this.client = client;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Invocation invocation = Invocations.parseInvocation(method, args);
            Request request = new Request(invocation);
            Object body = client.send(request).getBody();
            if (body instanceof ApiResult) {
                return handleApiResult((ApiResult) body);
            }
            return body;
        }

        private Object handleApiResult(ApiResult apiResult) throws Throwable {
            if (apiResult.isSuccess()) {
                return apiResult.getValue();
            }
            throw apiResult.getThrowable();
        }
    }

}
