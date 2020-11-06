package com.github.seng.common.exception;

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