package com.github.seng.core.utils;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

/**
 * @author wangyongxu
 */
public class ClassUtils {

    public static Class<?> getClass(String className) {
        try {
            return org.apache.commons.lang3.ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("class not found. ", e);
        }
    }

    public static Method getMethod(Class<?> className, String methodName, Class<?>[] args) {
        return MethodUtils.getMatchingMethod(className, methodName, args);
    }
}
