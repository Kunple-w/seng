package com.github.seng.core.register;

import com.github.seng.core.exception.SengRuntimeException;
import com.github.seng.core.register.exception.RegistryCreatedFailed;
import com.github.seng.core.rpc.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyongxu
 */
public abstract class AbstractRegistryFactory implements RegistryFactory {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<URL, RegisterService> cache = new HashMap<>();

    @Override
    public synchronized RegisterService getRegistry(URL url) {
        RegisterService registry = cache.get(url);
        if (registry == null) {
            try {
                registry = createRegistry(url);
                cache.put(url, registry);
            } catch (RegistryCreatedFailed e) {
                throw new SengRuntimeException(e);
            }
        }
        return registry;
    }

    /**
     * create registry
     *
     * @param url : url
     * @return com.github.seng.core.register.RegisterService
     * @throws RegistryCreatedFailed, if create failed
     * @author wangyongxu
     */
    public abstract RegisterService createRegistry(URL url) throws RegistryCreatedFailed;
}
