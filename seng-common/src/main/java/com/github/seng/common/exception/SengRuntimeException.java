package com.github.seng.common.exception;

/**
 * 僧 运行时异常
 *
 * @author wangyongxu
 */
public class SengRuntimeException extends RuntimeException {

    public SengRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SengRuntimeException(String message) {
        super(message);
    }

    public SengRuntimeException(Throwable cause) {
        super(cause);
    }
}
