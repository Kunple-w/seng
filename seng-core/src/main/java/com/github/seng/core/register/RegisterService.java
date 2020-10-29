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
     * lookup serviceName for urls
     *
     * @param serviceName : serviceName
     * @return java.util.List<com.github.seng.core.rpc.URL>
     * @author wangyongxu
     */
    List<URL> lookup(String serviceName);

    /**
     * subscribe service
     *
     * @param serviceName   : serviceName
     * @param eventListener : eventListener
     * @author wangyongxu
     */
    void subscribe(String serviceName, EventListener eventListener);

    /**
     * unsubscribe service
     *
     * @param serviceName   : serviceName
     * @param eventListener : eventListener
     * @author wangyongxu
     */
    void unsubscribe(String serviceName, EventListener eventListener);

}
