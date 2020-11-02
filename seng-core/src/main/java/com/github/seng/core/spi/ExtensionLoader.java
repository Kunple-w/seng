package com.github.seng.core.spi;

/**
 * spi 加载器
 *
 * @author wangyongxu
 * @date 2020/9/14 19:41
 */
public class ExtensionLoader {

    public static <T> T load(Class<T> cls, ClassLoader classLoader) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return null;
    }

}
