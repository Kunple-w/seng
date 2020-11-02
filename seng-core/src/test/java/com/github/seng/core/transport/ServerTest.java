package com.github.seng.core.transport;

import com.github.seng.core.UserService;
import com.github.seng.core.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

class ServerTest {

    private static final Logger logger = LoggerFactory.getLogger(ServerTest.class);

    @Test
    void start() throws InterruptedException {
        logger.info("server start");
        Server server = new Server();
        server.start(new InetSocketAddress(13232));
        register(server);
        TimeUnit.HOURS.sleep(100);
    }

    public void register(Server server) {
        UserService userService = new UserServiceImpl();
        server.registerService(userService);
    }
}