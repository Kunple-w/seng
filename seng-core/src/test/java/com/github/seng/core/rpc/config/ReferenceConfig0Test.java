package com.github.seng.core.rpc.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReferenceConfig0Test {

    private int port = 20000;

    @Test
    void refer() {
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://localhost:2181");
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setHost("localhost");
        serviceConfig.setPort(port);
        serviceConfig.setProtocol("seng");
//        new ReferenceConfig0<>()
    }
}