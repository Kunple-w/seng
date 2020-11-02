package com.github.seng.core.transport;

import com.github.seng.core.UserService;
import com.github.seng.core.rpc.Reference;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

class ClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

    @Test
    void start() throws InterruptedException {
        Client client = new Client();
        client.start(new InetSocketAddress(13232));
        TimeUnit.SECONDS.sleep(2);
        logger.info("channel: {}", client.getChannel());


        for (int i = 0; i < 2; i++) {
            SengMessage sengMessage = new SengMessage();
            sengMessage.setHeader(new SengProtocolHeader());
            sengMessage.setBody(new byte[0]);
//            client.send(sengMessage);
        }
        TimeUnit.HOURS.sleep(100);
    }

    @Test
    void remote() throws InterruptedException {
        Client client = new Client();
        client.start(new InetSocketAddress(13232));
        TimeUnit.SECONDS.sleep(2);

        Reference reference = new Reference();
        UserService refer = reference.refer(client, UserService.class);
        String hello = refer.hello("remote, ");
        logger.info("result: {}", hello);
    }
}