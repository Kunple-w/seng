package com.github.seng.core.rpc;

import com.github.seng.common.URL;
import com.github.seng.core.UserService;
import com.github.seng.core.UserServiceImpl;
import com.github.seng.core.register.DefaultProvider;
import com.github.seng.core.transport.ApiResult;
import com.github.seng.core.transport.Invocation;
import com.github.seng.core.transport.Invocations;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class RemoteInvokerTest {
    private static final Logger logger = LoggerFactory.getLogger(RemoteInvokerTest.class);

    @Test
    void call() {
        String a = "seng://192.168.73.65:18000/com.github.seng.core.UserService?methods=hello,search,hi,hi";
        URL url = URL.of(a);
        Object[] args = new Object[1];
        args[0] = "world2";
        Invocation invocation = Invocations.parseInvocation(UserService.class, "hello", args);
        RemoteInvoker<UserService> invoker = new RemoteInvoker<>(url, UserService.class);
        invoker.init();
        ApiResult apiResult = invoker.call(invocation);
        logger.info("apiResult: {}", apiResult);
    }
}