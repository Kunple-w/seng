package com.github.seng.core.transport;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class Server0Test {

    @Test
    void start() throws InterruptedException {
        Server0 server0 = new Server0();
        server0.start(new InetSocketAddress(29001));
        Thread.currentThread().join();
    }
}