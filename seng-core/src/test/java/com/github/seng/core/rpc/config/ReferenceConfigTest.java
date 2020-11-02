package com.github.seng.core.rpc.config;

import com.github.seng.core.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReferenceConfigTest {

    @Test
    public void test() {
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://localhost:2181");
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>(UserService.class, registryConfig);
        UserService userService = referenceConfig.get();
    }
}