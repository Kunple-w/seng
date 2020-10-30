package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;
import com.github.seng.core.rpc.exception.ServiceNotRegisterException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangyongxu
 */
public class LocalRegisterService implements RegisterService {

    // TODO: 2020-10-30 09:39:03 线程池配置 by wangyongxu
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    protected Map<String, Set<URL>> cache = new HashMap<>();

    protected Map<URL, Set<EventListener>> listenerMap = new HashMap<>();

    @Override
    public void register(URL url) {
        checkUrl(url);
        String serviceKey = getServiceKey(url);
        cache.compute(serviceKey, (k, v) -> {
            if (v == null) {
                Set<URL> newSet = new HashSet<>();
                Set<URL> old = new HashSet<>(newSet);
                newSet.add(url);
                serviceChanged(new URLChanged(new HashSet<>(newSet)), url);
                return newSet;
            } else {
                Set<URL> old = new HashSet<>(v);
                v.add(url);
                serviceChanged(new URLChanged(new HashSet<>(v)), url);
                return v;
            }
        });
    }

    protected String getServiceKey(URL url) {
        return url.getParam(URLConstant.APPLICATION_KEY);
    }

    private void checkUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url can't be null");
        }
    }

    @Override
    public void unregister(URL url) {
        checkUrl(url);
        String serviceKey = getServiceKey(url);
        cache.computeIfPresent(serviceKey, (k, v) -> {
            Set<URL> old = new HashSet<>(v);
            v.remove(url);
            serviceChanged(new URLChanged(new HashSet<>(v)), url);
            return v;
        });
    }

    @Override
    public List<URL> lookup(URL url) {
        return new ArrayList<>(cache.getOrDefault(url, Collections.emptySet()));
    }

    private class NotifyWorker implements Runnable {
        private final URLChanged changed;
        private URL url;

        public NotifyWorker(URLChanged changed, URL url) {
            this.changed = changed;
            this.url = url;
        }

        @Override
        public void run() {
            Set<EventListener> listeners = listenerMap.get(url);
            if (listeners != null && !listeners.isEmpty()) {
                for (EventListener listener : listeners) {
                    listener.onEvent(changed);
                }
            }
        }
    }

    protected void serviceChanged(URLChanged urlChanged, URL url) {
        executorService.execute(new NotifyWorker(urlChanged, url));
    }

    @Data
    @AllArgsConstructor
    public static class URLChanged {
        //        private String serviceKey;
//        private Set<URL> old;
        private Set<URL> now;

    }

    @Override
    public void subscribe(URL url, EventListener eventListener) {
        if (url == null) {
            throw new IllegalArgumentException("serviceName is null");
        }
        List<URL> urls = lookup(url);
        if (urls.isEmpty()) {
            throw new ServiceNotRegisterException(url + "not register");
        }

        listenerMap.compute(url, (k, v) -> {
            if (v == null) {
                Set<EventListener> newSet = new HashSet<>();
                newSet.add(eventListener);
                return newSet;
            } else {
                v.add(eventListener);
                return v;
            }
        });
    }

    @Override
    public void unsubscribe(URL url, EventListener eventListener) {
        if (url == null) {
            throw new IllegalArgumentException("serviceName is null");
        }
        listenerMap.computeIfPresent(url, (k, v) -> {
            v.remove(eventListener);
            return v;
        });
    }
}
