package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;
import com.github.seng.core.spi.SPI;
import com.github.seng.core.spi.Scope;

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
     * @return com.github.seng.core.register.RegisterService
     * @author wangyongxu
     */
    RegisterService getRegistry(URL url);
}
