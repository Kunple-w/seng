package com.github.seng.core.rpc.exception;


import com.github.seng.common.exception.SengRuntimeException;

/**
 * @author wangyongxu
 */
public class ServiceNotRegisterException extends SengRuntimeException {
    public ServiceNotRegisterException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceNotRegisterException(String message) {
        super(message);
    }
}
