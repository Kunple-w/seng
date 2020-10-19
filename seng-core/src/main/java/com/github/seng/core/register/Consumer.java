package com.github.seng.core.register;

import com.github.seng.core.transport.Request;
import com.github.seng.core.transport.Response;

/**
 * consumer
 *
 * @author wangyongxu
 */
public interface Consumer extends Node {

    Response call(Request request);
}
