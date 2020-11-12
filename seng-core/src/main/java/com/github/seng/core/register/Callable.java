package com.github.seng.core.register;

import com.github.seng.common.Node;
import com.github.seng.core.transport.ApiResult;
import com.github.seng.core.transport.Invocation;

/**
 * consumer
 *
 * @author wangyongxu
 */
public interface Callable extends Node {

    ApiResult call(Invocation invocation);
}
