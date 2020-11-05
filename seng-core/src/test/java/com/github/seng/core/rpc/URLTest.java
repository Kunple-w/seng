package com.github.seng.core.rpc;

import com.github.seng.common.URL;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLTest {

    private String seng = "seng://localhost:18080/com.github.seng.executor?limit=10&timeout=100s";
    private String seng2 = "seng://admin:Hello1234@localhost:18080/com.github.seng.executor?limit=10&timeout=100s";

    @Test
    void getURL() {
        URL of = URL.of(seng);
        Map<String, String> param = new HashMap<>();
        URL url = new URL("seng", "localhost", 18080, "com.github.seng.executor");
        assertEquals(of, url);
    }

    @Test
    void testOf2() {
        URL of = URL.of(seng2);
        URL url = new URL("seng", "localhost", 18080, "admin", "Hello1234", "com.github.seng.executor");
        assertEquals("admin", of.getUsername());
        assertEquals("Hello1234", of.getPassword());
        assertEquals(of, url);
    }

    @Test
    void testOf3() {
        String str = "zookeeper://localhost:2181";
        URL of = URL.of(str);
        URL url = new URL("zookeeper", "localhost", 2181, "/");
        assertEquals(of, url);
    }
}