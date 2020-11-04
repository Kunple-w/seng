package com.github.seng.registry.zookeeper;

import com.github.seng.core.register.RegisterService;
import com.github.seng.core.register.RegistryFactory;
import com.github.seng.core.rpc.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class ZookeeperRegistryFactoryTest {

    @Test
    void createRegistry() {
        String a = "zookeeper://localhost:2181";
        RegistryFactory zookeeperRegistryFactory = new ZookeeperRegistryFactory();
        RegisterService registry = zookeeperRegistryFactory.getRegistry(URL.of(a));
        List<URL> lookup = registry.lookup(URL.of(a));
        Assertions.assertEquals(Collections.emptyList(), lookup, "look up is not empty");
    }
}