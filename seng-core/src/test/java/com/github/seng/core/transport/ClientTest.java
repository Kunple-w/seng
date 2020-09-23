package com.github.seng.core.transport;

import io.netty.buffer.ByteBufUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

    @Test
    void start() throws InterruptedException {
        Client client = new Client();
        client.start(new InetSocketAddress(13232));
        TimeUnit.SECONDS.sleep(2);
        logger.info("channel: {}", client.getChannel());
        client.getChannel().write(new SengMessage());
        for (int i = 0; i < 2; i++) {
            client.send(new SengMessage());
        }
        TimeUnit.HOURS.sleep(100);

    }
}