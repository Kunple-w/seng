package com.github.seng.core.rpc;

import com.github.seng.common.URL;
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
        Server server = new Server(new InetSocketAddress(port));
        server.start();
        UserServiceImpl userService = new UserServiceImpl();
        Exporter<UserService> exporter = new Exporter<>(UserService.class, userService);
        exporter.setProtocol("seng");
        exporter.setPort(port);
        exporter.setServer(server);

        exporter.export();
        URL url = exporter.getURL();
        logger.info("url: {}", url.getFullURL());
        TimeUnit.HOURS.sleep(100);
    }

    @Test
    void reference() throws Exception {
        Client client = new Client(new InetSocketAddress(port));
        client.start();
        TimeUnit.SECONDS.sleep(2);

        Reference<UserService> reference = new Reference<>(client, UserService.class);
        UserService refer = reference.refer();
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

    @Test
    void testExport2() {
    }

    @Test
    void unExport() {
    }
}