package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;
import com.github.seng.core.rpc.exception.ServiceMethodNotExistedException;
import com.github.seng.core.transport.ApiResult;
import com.github.seng.core.transport.Invocation;
import com.github.seng.core.transport.Request;
import com.github.seng.core.transport.Response;

import java.lang.reflect.Method;

/**
 * @author wangyongxu
 */
public class DefaultProvider<T> extends AbstractProvider<T> {

    protected final T impl;

    public DefaultProvider(URL url, Class<T> cls, T impl) {
        super(url, cls);
        this.impl = impl;
    }

    @Override
    public T getImpl() {
        return impl;
    }

    @Override
    public Response call(Request request) {
        Invocation invocation = request.getBody();
        Method method = lookupMethod(invocation.getMethodName(), invocation.getArgsDesc());
        Response response = new Response();

        if (method == null) {
            ApiResult apiResult = ApiResult.exception(new ServiceMethodNotExistedException(invocation.toString() + " not existed"));
            response.setBody(apiResult);
            return response;
        }
        try {
            Object result = method.invoke(impl, invocation.getArgs());
            ApiResult apiResult = ApiResult.success(result);
            response.setBody(apiResult);
            return response;
        } catch (Exception e) {
            ApiResult apiResult = ApiResult.exception(e);
            response.setBody(apiResult);
            return response;
        }
    }
}
