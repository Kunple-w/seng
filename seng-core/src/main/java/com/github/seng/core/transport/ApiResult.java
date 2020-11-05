package com.github.seng.core.transport;

import lombok.Data;

/**
 * @author wangyongxu
 */
@Data
public class ApiResult {

    private static final int SUCCESS = 0;
    private static final int FAILED = -1;

    private Object value;

    private int code;

    private Throwable throwable;

    public ApiResult(Object value, int code, Throwable throwable) {
        this.value = value;
        this.code = code;
        this.throwable = throwable;
    }

    public static ApiResult success(Object value) {
        return new ApiResult(value, SUCCESS, null);
    }

    public static ApiResult exception(Throwable throwable) {
        return new ApiResult(null, FAILED, throwable);
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }
}
