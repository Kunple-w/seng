package com.github.seng.core.register;

import com.github.seng.core.transport.Client;

/**
 * @author wangyongxu
 */
public abstract class AbstractNode implements Node {
    private Client client;
    private String host;
    private int port;

    public AbstractNode(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

}
