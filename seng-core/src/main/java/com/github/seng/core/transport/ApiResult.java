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

    private Exception exception;

    public ApiResult(Object value, int code, Exception exception) {
        this.value = value;
        this.code = code;
        this.exception = exception;
    }

    public static ApiResult success(Object value) {
        return new ApiResult(value, SUCCESS, null);
    }

    public static ApiResult exception(Exception exception) {
        return new ApiResult(null, FAILED, exception);
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }
}
