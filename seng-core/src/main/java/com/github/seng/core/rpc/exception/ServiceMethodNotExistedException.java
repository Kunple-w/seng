package com.github.seng.core.rpc.exception;

/**
 * @author wangyongxu
 */
public class ServiceMethodNotExistedException extends ServiceNotRegisterException {
    public ServiceMethodNotExistedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceMethodNotExistedException(String message) {
        super(message);
    }
}
