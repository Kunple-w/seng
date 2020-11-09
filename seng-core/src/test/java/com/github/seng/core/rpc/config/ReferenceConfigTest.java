package com.github.seng.core.rpc.config;

import com.github.seng.core.UserService;
import com.github.seng.core.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReferenceConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(ReferenceConfigTest.class);
    private int port = 20000;

    @Test
    public void test() {
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://localhost:2181");
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setHost("localhost");
        serviceConfig.setPort(port);
        serviceConfig.setProtocol("seng");
        ExportConfig<UserService> exportConfig = new ExportConfig<>(UserService.class, new UserServiceImpl(), registryConfig, serviceConfig);
        exportConfig.export();

        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>(UserService.class, registryConfig);
        UserService userService = referenceConfig.refer();

        assertEquals("hello world", userService.hello("world"), "远程调用失败");
    }
}