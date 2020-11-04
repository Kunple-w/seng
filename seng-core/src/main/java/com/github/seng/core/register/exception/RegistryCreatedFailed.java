package com.github.seng.core.register.exception;

import com.github.seng.core.exception.SengException;

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
