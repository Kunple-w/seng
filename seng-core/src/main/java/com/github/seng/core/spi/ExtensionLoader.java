package com.github.seng.core.spi;

import com.github.seng.core.exception.SengRuntimeException;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * spi 加载器
 *
 * @author wangyongxu
 * @date 2020/9/14 19:41
 */
public class ExtensionLoader<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);
    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> loaders = new ConcurrentHashMap<>();

    private static final String PREFIX = "META-INF/services/";

    private Class<T> service;

    private ClassLoader cl;

    public static <T> T load(Class<T> cls, ClassLoader classLoader) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return null;
    }

    public static <T> ExtensionLoader<T> getLoader(String clsName) {

        return null;
    }

    public static <T> ExtensionLoader<T> getLoader(Class<T> cls) {
        return null;
    }

    public List<T> load(Class<T> cls) {
        return null;
    }

    public T load(String clsName) {
        return null;
    }


    private ConcurrentMap<String, Class<T>> loadExtensionClasses(String prefix) {
        String fullName = prefix + service.getName();
        List<String> classNames = new ArrayList<String>();

        try {
            Enumeration<URL> urls;
            if (cl == null) {
                urls = ClassLoader.getSystemResources(fullName);
            } else {
                urls = cl.getResources(fullName);
            }

            if (urls == null || !urls.hasMoreElements()) {
                return new ConcurrentHashMap<>();
            }

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

//                parseUrl(type, url, classNames);
            }
        } catch (Exception e) {
            throw new SengRuntimeException(
                    "loadExtensionClasses error, class: " + prefix + " type: " + service.getClass(), e);
        }

        return loadClass(classNames);
    }

    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Class<T>> loadClass(List<String> classNames) {
        ConcurrentMap<String, Class<T>> map = new ConcurrentHashMap<>();

        for (String className : classNames) {
            try {
                Class<T> clz = (Class<T>) forName(className, cl);
                checkExtensionType(clz);
                String spiName = null;
                // TODO: 2020-11-03 06:08:58  by wangyongxu

                if (map.containsKey(spiName)) {
                    fail(clz, ":Error spiName already exist " + spiName);
                } else {
                    map.put(spiName, clz);
                }
            } catch (Exception e) {
                fail(service, "Error load spi class", e);
            }
        }
        return map;
    }

    private void checkExtensionType(Class<T> clz) {
        checkClassPublic(clz);

        checkConstructorPublic(clz);

        checkClassInherit(clz);
    }

    private void checkClassInherit(Class<T> clz) {
        if (!service.isAssignableFrom(clz)) {
            fail(clz, "Error is not instanceof " + service.getName());
        }
    }

    private void checkClassPublic(Class<T> clz) {
        if (!Modifier.isPublic(clz.getModifiers())) {
            fail(clz, "Error is not a public class");
        }
    }

    private void checkConstructorPublic(Class<T> clz) {
        Constructor<?>[] constructors = clz.getConstructors();

        if (constructors.length == 0) {
            fail(clz, "Error has no public no-args constructor");
        }

        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterTypes().length == 0) {
                return;
            }
        }

        fail(clz, "Error has no public no-args constructor");
    }

    private Class<?> forName(String clsName, ClassLoader cl) {
        try {
            return Class.forName(clsName, false, cl);
        } catch (ClassNotFoundException e) {
            fail(service, "Provider " + clsName + " not found");
            return null;
        }
    }

    private Iterator<String> parse(URL u) {
        Set<String> names = new LinkedHashSet<>(); // preserve insertion order
        try {
            URLConnection uc = u.openConnection();
            uc.setUseCaches(false);
            try (InputStream in = uc.getInputStream();
                 BufferedReader r
                         = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                int lc = 1;
                while ((lc = parseLine(u, r, lc, names)) >= 0) ;
            }
        } catch (IOException x) {
            fail(service, "Error accessing configuration file", x);
        }
        return names.iterator();
    }

    private int parseLine(URL u, BufferedReader r, int lc, Set<String> names) throws IOException {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf('#');
        if (ci >= 0) ln = ln.substring(0, ci);
        ln = ln.trim();
        int n = ln.length();
        if (n != 0) {
            if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
                fail(service, u, lc, "Illegal configuration-file syntax");
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp))
                fail(service, u, lc, "Illegal provider-class name: " + ln);
            int start = Character.charCount(cp);
            for (int i = start; i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && (cp != '.'))
                    fail(service, u, lc, "Illegal provider-class name: " + ln);
            }
            names.add(ln);
        }
        return lc + 1;
    }

    private static void fail(Class<?> service, String msg, Throwable cause)
            throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg,
                cause);
    }

    private static void fail(Class<?> service, String msg)
            throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg);
    }

    private static void fail(Class<?> service, URL u, int line, String msg)
            throws ServiceConfigurationError {
        fail(service, u + ":" + line + ": " + msg);
    }
}
