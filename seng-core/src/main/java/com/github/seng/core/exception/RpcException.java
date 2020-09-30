package com.github.seng.core.exception;

/**
 * @author wangyongxu
 */
public class RpcException extends SengException {
    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
