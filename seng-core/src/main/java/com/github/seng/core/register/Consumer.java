package com.github.seng.core.register;

import com.github.seng.core.transport.ApiResult;
import com.github.seng.core.transport.Invocation;

/**
 * consumer
 *
 * @author wangyongxu
 */
public interface Consumer extends Node {

    ApiResult call(Invocation invocation);
}
