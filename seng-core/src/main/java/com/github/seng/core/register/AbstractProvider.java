package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;
import com.github.seng.core.utils.ReflectUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyongxu
 */
public abstract class AbstractProvider<T> implements Provider<T>, Consumer {

    protected Class<T> cls;

    protected boolean isAvailable = false;

    protected Map<String, Method> methodMap = new HashMap<>();

    protected URL url;

    public AbstractProvider(URL url, Class<T> cls) {
        this.url = url;
        this.cls = cls;
        init();
    }

    protected void init() {
        methodMap = ReflectUtils.getMethodListDesc(cls);
        isAvailable = true;
    }

    @Override
    public Class<T> getInterface() {
        return cls;
    }

    @Nullable
    @Override
    public Method lookupMethod(String methodName, String paramDesc) {
        String methodSignature = ReflectUtils.getMethodSignature(methodName, paramDesc);
        return methodMap.get(methodSignature);
    }


    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public URL getURL() {
        return url;
    }

    @Override
    public String toString() {
        return url.toString();
    }

    @Override
    public void destroy() {
        isAvailable = false;
    }
}