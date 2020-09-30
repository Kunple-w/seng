package com.github.seng.core.rpc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLTest {

    private String seng = "seng://localhost:18080/com.github.seng.executor?limit=10&timeout=100s";

    @Test
    void getURL() {
        URL of = URL.of(seng);
        Map<String, String> param = new HashMap<>();
        URL url = new URL("seng", "localhost", 18080, "com.github.seng.executor");
        assertEquals(of, url);
    }
}