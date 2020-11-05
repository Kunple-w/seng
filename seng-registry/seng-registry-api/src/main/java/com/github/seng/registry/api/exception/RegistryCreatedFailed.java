package com.github.seng.registry.api.exception;

import com.github.seng.common.exception.SengException;

/**
 * @author wangyongxu
 */
public class RegistryCreatedFailed extends SengException {
    public RegistryCreatedFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryCreatedFailed(String message) {
        super(message);
    }
}
