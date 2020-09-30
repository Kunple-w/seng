package com.github.seng.core.rpc;

import lombok.Data;

/**
 * @author wangyongxu
 */
@Data
public class RpcResult {

    private Object value;

    private Throwable exception;

    public boolean hasException() {
        return exception == null;
    }

}
