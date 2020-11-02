package com.github.seng.core.rpc;

import com.github.seng.core.UserService;
import com.github.seng.core.UserServiceImpl;
import com.github.seng.core.transport.Client;
import com.github.seng.core.transport.Server;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

class ExporterTest {
    private static final Logger logger = LoggerFactory.getLogger(ExporterTest.class);

    private int port = 18000;

    @Test
    void export() throws InterruptedException {
        Exporter exporter = new Exporter();
        Server server = new Server();
        server.start(new InetSocketAddress(port));
        exporter.export(server, new UserServiceImpl());
        TimeUnit.HOURS.sleep(100);
    }

    @Test
    void reference() throws InterruptedException {
        Client client = new Client();
        client.start(new InetSocketAddress(port));
        TimeUnit.SECONDS.sleep(2);

        Reference reference = new Reference();
        UserService refer = reference.refer(client, UserService.class);
        String hello = refer.hello("seng!");
        logger.info("result: {}", hello);
    }

    @Test
    void testExport() {
    }

    @Test
    void testExport1() {
    }

    @Test
    void register() {
    }
}