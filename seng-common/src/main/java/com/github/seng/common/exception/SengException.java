package com.github.seng.common.exception;

/**
 * Seng受检异常类
 *
 * @author wangyongxu
 */
public class SengException extends Exception {
    public SengException() {
    }

    public SengException(String message) {
        super(message);
    }

    public SengException(String message, Throwable cause) {
        super(message, cause);
    }

    public SengException(Throwable cause) {
        super(cause);
    }

    public SengException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
