package com.github.seng.core.rpc.config;

import com.github.seng.core.UserService;
import com.github.seng.core.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ReferenceConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(ReferenceConfigTest.class);
    private int port = 20000;

    @Test
    public void test() {
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://localhost:2181");
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setHost("localhost");
        serviceConfig.setPort(port);
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>(UserService.class, registryConfig, serviceConfig);
        referenceConfig.setImpl(new UserServiceImpl());
        UserService userService = referenceConfig.get();
        assertEquals("hello world", userService.hello("world"), "远程调用失败");
    }
}