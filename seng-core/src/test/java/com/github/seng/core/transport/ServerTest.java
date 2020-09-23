package com.github.seng.core.transport;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private static final Logger logger = LoggerFactory.getLogger(ServerTest.class);
    @Test
    void start() throws InterruptedException {
        logger.info("server start");
        new Server().start(new InetSocketAddress(13232));
        TimeUnit.HOURS.sleep(100);
    }
}