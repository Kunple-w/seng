package com.github.seng.registry.api;

import com.github.seng.common.URL;
import com.github.seng.common.spi.SPI;
import com.github.seng.common.spi.Scope;

/**
 * registry factory
 *
 * @author wangyongxu
 */
@SPI(scope = Scope.SINGLETON)
public interface RegistryFactory {

    /**
     * get registry
     *
     * @param url : url
     * @return com.github.seng.registry.api.RegisterService
     * @author wangyongxu
     */
    RegisterService getRegistry(URL url);
}
