package com.github.seng.core.exception;

/**
 * 僧 运行时异常
 *
 * @author wangyongxu
 */
public class SengRuntimeException extends RuntimeException {

    public SengRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
