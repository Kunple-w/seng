package com.github.seng.core.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

class NetUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(NetUtilsTest.class);

    @Test
    void getLocalHost() {
        String localHost = NetUtils.getLocalHost();
        logger.info("{}", localHost);
    }

    @Test
    void getLocalAddress() {
        InetAddress inetAddress = NetUtils.getLocalAddress();
        logger.info("{}", inetAddress);
    }
}