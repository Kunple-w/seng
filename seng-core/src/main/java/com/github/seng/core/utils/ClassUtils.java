package com.github.seng.core.utils;

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
}
