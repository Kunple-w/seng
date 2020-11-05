package com.github.seng.core.transport;

import java.net.InetSocketAddress;

/**
 * @author wangyongxu
 */
public class EndPointFactoryImpl implements EndPointFactory {
    @Override
    public Server createServer(int port) {
        Server server = new Server();
        server.start(new InetSocketAddress(port));
        return server;
    }

    @Override
    public Client createClient(InetSocketAddress remote) {
        Client client = new Client();
        client.start(remote);
        return client;
    }
}
