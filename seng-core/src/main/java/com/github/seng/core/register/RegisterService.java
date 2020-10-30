package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;

import java.util.List;

/**
 * register service
 *
 * @author wangyongxu
 */
public interface RegisterService {


    /**
     * unregister service
     *
     * @param url :
     * @author wangyongxu
     */
    void register(URL url);

    /**
     * unregister service
     *
     * @param url :
     * @author wangyongxu
     */
    void unregister(URL url);

    /**
     * lookup url for urls
     *
     * @param url : url
     * @return java.util.List<com.github.seng.core.rpc.URL>
     * @author wangyongxu
     */
    List<URL> lookup(URL url);

    /**
     * subscribe service
     *
     * @param url           : url
     * @param eventListener : eventListener
     * @author wangyongxu
     */
    void subscribe(URL url, EventListener eventListener);

    /**
     * unsubscribe service
     *
     * @param url           : serviceName
     * @param eventListener : eventListener
     * @author wangyongxu
     */
    void unsubscribe(URL url, EventListener eventListener);

}
